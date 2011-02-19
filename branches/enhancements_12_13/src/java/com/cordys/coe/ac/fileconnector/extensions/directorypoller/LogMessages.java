/**
 * Copyright 2005 Cordys R&D B.V. 
 * 
 * This file is part of the Cordys SAP Connector. 
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
 package com.cordys.coe.ac.fileconnector.extensions.directorypoller;

import com.eibus.localization.message.Message;
import com.eibus.localization.message.MessageSet;

/**
 * This code is generated by running com.cordys.coe.cep.wizards.localization.CoEMessageGenerator.
 */
public class LogMessages
{
    /**
     * Holds the definition of the message set.
     */
    public static final MessageSet MESSAGE_SET = MessageSet.getMessageSet("com.cordys.coe.ac.fileconnector.extensions.directorypoller.LogMessages");

    /**
     * Holds the definition of the message with ID CONNECTOR_MANAGEMENT_DESCRIPTION. Message text:
     * Directory Poller
     */
    public static final Message CONNECTOR_MANAGEMENT_DESCRIPTION = MESSAGE_SET.getMessage("CONNECTOR_MANAGEMENT_DESCRIPTION");
    /**
     * Holds the definition of the message with ID CNTR_FILE_SCAN_TIME. Message text: Time used for
     * scanning files in the input folder(s).
     */
    public static final Message CNTR_FILE_SCAN_TIME = MESSAGE_SET.getMessage("CNTR_FILE_SCAN_TIME");
    /**
     * Holds the definition of the message with ID CNTR_TRIGGER_TIME. Message text: Duration of SOAP
     * message trigger.
     */
    public static final Message CNTR_TRIGGER_TIME = MESSAGE_SET.getMessage("CNTR_TRIGGER_TIME");
    /**
     * Holds the definition of the message with ID CNTR_INPUT_FILES. Message text: Number of seen
     * input files
     */
    public static final Message CNTR_INPUT_FILES = MESSAGE_SET.getMessage("CNTR_INPUT_FILES");
    /**
     * Holds the definition of the message with ID CNTR_RESTARTED_FILES. Message text: Number of
     * times file processing is restarted after connector restart.
     */
    public static final Message CNTR_RESTARTED_FILES = MESSAGE_SET.getMessage("CNTR_RESTARTED_FILES");
    /**
     * Holds the definition of the message with ID CNTR_PROCESSED_FILES_SUCCESS. Message text:
     * Number of successfully processed files
     */
    public static final Message CNTR_PROCESSED_FILES_SUCCESS = MESSAGE_SET.getMessage("CNTR_PROCESSED_FILES_SUCCESS");
    /**
     * Holds the definition of the message with ID CNTR_PROCESSED_FILES_ERROR. Message text: Number
     * of processed files moved to the error folder
     */
    public static final Message CNTR_PROCESSED_FILES_ERROR = MESSAGE_SET.getMessage("CNTR_PROCESSED_FILES_ERROR");
    /**
     * Holds the definition of the message with ID CNTR_PROCESSED_FILES_TOTAL. Message text: Total
     * number of processed files
     */
    public static final Message CNTR_PROCESSED_FILES_TOTAL = MESSAGE_SET.getMessage("CNTR_PROCESSED_FILES_TOTAL");
    /**
     * Holds the definition of the message with ID CNTR_PROCESSING_TIME. Message text: Average
     * processing time. This does not include the scanning time.
     */
    public static final Message CNTR_PROCESSING_TIME = MESSAGE_SET.getMessage("CNTR_PROCESSING_TIME");
    /**
     * Holds the definition of the message with ID CNTR_FILE_SIZE. Message text: Average incoming
     * file size
     */
    public static final Message CNTR_FILE_SIZE = MESSAGE_SET.getMessage("CNTR_FILE_SIZE");
    /**
     * Holds the definition of the message with ID CNTR_CUR_WORKER_THREADS. Message text: Number of
     * active worker threads.
     */
    public static final Message CNTR_CUR_WORKER_THREADS = MESSAGE_SET.getMessage("CNTR_CUR_WORKER_THREADS");
    /**
     * Holds the definition of the message with ID CNTR_CUR_WORK_QUEUE_SIZE. Message text: Current
     * size of the thread pool queue.
     */
    public static final Message CNTR_CUR_WORK_QUEUE_SIZE = MESSAGE_SET.getMessage("CNTR_CUR_WORK_QUEUE_SIZE");
    /**
     * Holds the definition of the message with ID CNTR_CUR_FILES_IN_PROCESS. Message text: Number
     * of files in the processing folder.
     */
    public static final Message CNTR_CUR_FILES_IN_PROCESS = MESSAGE_SET.getMessage("CNTR_CUR_FILES_IN_PROCESS");
    /**
     * Holds the definition of the message with ID ALERT_FILE_ERROR. Message text: Processing of
     * file {0} ended in an error: {1}
     */
    public static final Message ALERT_FILE_ERROR = MESSAGE_SET.getMessage("ALERT_FILE_ERROR");
    /**
     * Holds the definition of the message with ID ALERT_FILE_ERROR_DESC. Message text: File
     * processing error
     */
    public static final Message ALERT_FILE_ERROR_DESC = MESSAGE_SET.getMessage("ALERT_FILE_ERROR_DESC");
    /**
     * Holds the definition of the message with ID HOTSETTING_NUM_WORKERS. Message text: Number of
     * worker threads.
     */
    public static final Message HOTSETTING_NUM_WORKERS = MESSAGE_SET.getMessage("HOTSETTING_NUM_WORKERS");
    /**
     * Holds the definition of the message with ID HOTSETTING_MIN_WORKERS. Message text: Minumum
     * number of worker threads.
     */
    public static final Message HOTSETTING_MIN_WORKERS = MESSAGE_SET.getMessage("HOTSETTING_MIN_WORKERS");
    /**
     * Holds the definition of the message with ID HOTSETTING_MAX_WORKERS. Message text: Maxumum
     * number of worker threads.
     */
    public static final Message HOTSETTING_MAX_WORKERS = MESSAGE_SET.getMessage("HOTSETTING_MAX_WORKERS");
}