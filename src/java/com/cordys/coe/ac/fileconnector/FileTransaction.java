/**
 * Copyright 2005 Cordys R&D B.V. 
 * 
 * This file is part of the Cordys File Connector. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.cordys.coe.ac.fileconnector;

import com.cordys.coe.ac.fileconnector.exception.FileException;
import com.cordys.coe.exception.GeneralException;
import com.cordys.coe.util.XMLProperties;

import com.eibus.connector.nom.Connector;

import com.eibus.security.ac.AccessControlObject;

import com.eibus.soap.ApplicationTransaction;
import com.eibus.soap.BodyBlock;

import com.eibus.util.logger.CordysLogger;
import com.eibus.util.logger.Severity;

import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Find;
import com.eibus.xml.nom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * FileConnector connector transaction object. Receives the SOAP request and passes it on to the
 * handling method.
 *
 * @author  mpoyhone
 */
public class FileTransaction
    implements ApplicationTransaction
{
    /**
     * Contains the valid request types for this transaction.
     */
    private static final Map<String, String> mRequestTypes = new HashMap<String, String>();

    static
    {
        mRequestTypes.put("FILE", "FILE");
    }

    /**
     * Identifies the Logger.
     */
    private static CordysLogger LOGGER = CordysLogger.getCordysLogger(FileConnector.class);
    /**
     * Application connector configuration object.
     */
    private ApplicationConfiguration acConfig;
    /**
     * The SOAP reqeust ACL Object that was passed to this connector.
     */
    private AccessControlObject acoTxnAcl;
    /**
     * The connector object for SOAP connections.
     */
    private Connector cConnector;
    /**
     * File connector instance for this transaction.
     */
    private FileConnector fcFileConnector;

    /**
     * Constructor for the FileConnector transaction object.
     *
     * @param  fcFileConnector  File connector instance for this transaction.
     * @param  cConnector       The connector object used for SOAP calls.
     * @param  acoTxnAccess     ACL object for this transaction.
     * @param  acConfig         Application configuration object for this SOAP processor.
     */
    public FileTransaction(FileConnector fcFileConnector, Connector cConnector,
                           AccessControlObject acoTxnAccess, ApplicationConfiguration acConfig)
    {
        this.fcFileConnector = fcFileConnector;
        this.cConnector = cConnector;
        this.acConfig = acConfig;
        this.acoTxnAcl = acoTxnAccess;
    }

    /**
     * Aborts the transaction.
     */
    public void abort()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("FileTransaction abort.");
        }
    }

    /**
     * Checks if this object understands the transaction of this type.
     *
     * @param   sType  Transaction type
     *
     * @return  Returns true, if we can process this kind of request.
     */
    public boolean canProcess(String sType)
    {
        if (mRequestTypes.containsKey(sType))
        {
            return true;
        }

        return false;
    }

    /**
     * Commits the transaction.
     */
    public void commit()
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("FileTransaction commit.");
        }
    }

    /**
     * Processes the actual SOAP request. This is the main entry point for the transaction
     *
     * @param   bbRequest   The received SOAP message
     * @param   bbResponse  The SOAP reply message generated by the transaction.
     *
     * @return  Returns true, if the transaction succeeded
     */
    public boolean process(BodyBlock bbRequest, BodyBlock bbResponse)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("FileTransaction starting. Incoming request: " +
                         Node.writeToString(bbRequest.getXMLNode(), true));
        }

        boolean bResult = false;

        try
        {
            int iReqNode = bbRequest.getXMLNode();

            if (iReqNode == 0)
            {
                throw new FileException("No actions specified in the request.");
            }

            String sAction = getActionFromImplementation(bbRequest);

            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Action is " + sAction);
            }

            IFileConnectorMethod method = fcFileConnector.getMethod(sAction);

            // Pass the control to the right handler object.
            if (method != null)
            {
                ISoapRequestContext req = new SoapRequestImpl(bbRequest, bbResponse);

                bResult = method.process(req) == IFileConnectorMethod.EResult.FINISHED;
            }
            else
            {
                throw new FileException("Unknown action '" + sAction + "'");
            }
        }
        catch (Throwable e)
        {
            if (LOGGER.isWarningEnabled())
            {
                LOGGER.warn(e, LogMessages.TRANSACTION_FAILED);
            }

            bbResponse.createSOAPFault("Server.Exception", e.toString());

            return false;
        }

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("FileTransaction finished. SOAP response: " +
                         Node.writeToString(bbResponse.getXMLNode(), true));
        }

        return bResult;
    }

    /**
     * Returns the ACL object.
     *
     * @return  The ACL object.
     */
    public AccessControlObject getAcl()
    {
        return acoTxnAcl;
    }

    /**
     * Returns the connector configuration object.
     *
     * @return  The connector configuration object.
     */
    public ApplicationConfiguration getConfig()
    {
        return acConfig;
    }

    /**
     * Returns the Connector object.
     *
     * @return  The Connector object.
     */
    public Connector getConnector()
    {
        return cConnector;
    }

    /**
     * Returns the SOAP request method name from the method implementation or the SOAP request
     * method XML element name.
     *
     * @param   bbRequest  SOAP request.
     *
     * @return  Method name.
     */
    private String getActionFromImplementation(BodyBlock bbRequest)
    {
        String sAction;

        int iImplNode = bbRequest.getMethodDefinition().getImplementation();
        int actionNode = Find.firstMatch(iImplNode, "<implementation><action>");

        if (actionNode != 0)
        {
            sAction = Node.getData(actionNode);
        }
        else
        {
            // For backwards compatibility
            sAction = Node.getLocalName(bbRequest.getXMLNode());
        }

        return sAction;
    }

    /**
     * Implementation of the ISoapRequestContext for application connector SOAP requests.
     *
     * @author  mpoyhone
     */
    private class SoapRequestImpl
        implements ISoapRequestContext
    {
        /**
         * Request body block.
         */
        private BodyBlock requestBlock;
        /**
         * Response body block.
         */
        private BodyBlock responseBlock;

        /**
         * Constructor for SoapRequestImpl.
         *
         * @param  requestBlock
         * @param  responseBlock
         */
        public SoapRequestImpl(BodyBlock requestBlock, BodyBlock responseBlock)
        {
            super();
            this.requestBlock = requestBlock;
            this.responseBlock = responseBlock;
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#addResponseElement(java.lang.String)
         */
        public int addResponseElement(String elemName)
        {
            return Node.createElement(elemName, responseBlock.getXMLNode());
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#addResponseElement(int)
         */
        public int addResponseElement(int node)
        {
            return Node.appendToChildren(node, responseBlock.getXMLNode());
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#addResponseElement(java.lang.String,
         *       java.lang.String)
         */
        public int addResponseElement(String elemName, String elemValue)
        {
            return Node.createTextElement(elemName, elemValue, responseBlock.getXMLNode());
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getMethodImplementation()
         */
        public int getMethodImplementation()
        {
            return requestBlock.getMethodDefinition().getImplementation();
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getNomConnector()
         */
        public Connector getNomConnector()
        {
            return fcFileConnector.getNomConnector();
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getNomDocument()
         */
        public Document getNomDocument()
        {
            return Node.getDocument(responseBlock.getXMLNode());
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getRequestAsXmlProperties()
         */
        public XMLProperties getRequestAsXmlProperties()
                                                throws GeneralException
        {
            return new XMLProperties(requestBlock.getXMLNode());
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getRequestOrganizationDn()
         */
        public String getRequestOrganizationDn()
        {
            return fcFileConnector.getProcessor().getOrganization();
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getRequestRootNode()
         */
        public int getRequestRootNode()
        {
            return requestBlock.getXMLNode();
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getRequestUserDn()
         */
        public String getRequestUserDn()
        {
            return requestBlock.getSOAPTransaction().getUserCredentials().getOrganizationalUser();
        }

        /**
         * @see  com.cordys.coe.ac.fileconnector.ISoapRequestContext#getResponseRootNode()
         */
        public int getResponseRootNode()
        {
            return responseBlock.getXMLNode();
        }
    }
}
