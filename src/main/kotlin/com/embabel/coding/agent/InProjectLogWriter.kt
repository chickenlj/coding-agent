/*
 * Copyright 2024-2025 Embabel Software, Inc.
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
package com.embabel.coding.agent

import com.embabel.coding.domain.SoftwareProject
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

/**
 * Write to the /.embabel/log.jsonl file in the focus project
 */
@Service
class InProjectLogWriter(
    private val path: String = ".embabel/log.jsonl",
    private val objectMapper: ObjectMapper,
) : LogWriter {

    override fun logRequest(
        request: CodeModificationRequest,
        softwareProject: SoftwareProject,
    ) {
        val jsonLine = objectMapper.writeValueAsString(request) + "\n"
        softwareProject.appendToFile(
            path = path,
            content = jsonLine,
            createIfNotExists = true,
        )
    }

    override fun logResponse(
        request: SuccessfulCodeModification,
        softwareProject: SoftwareProject,
    ) {
        val jsonLine = objectMapper.writeValueAsString(request) + "\n"
        softwareProject.appendToFile(path = path, content = jsonLine, createIfNotExists = true)
    }
}
