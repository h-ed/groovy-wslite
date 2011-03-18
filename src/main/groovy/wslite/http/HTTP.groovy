/* Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wslite.http

class HTTP {

    static final String DEFAULT_CHARSET = "ISO-8859-1" // http://tools.ietf.org/html/rfc2616#section-3.7.1

    static String parseMimeTypeFromContentType(String contentType) {
        int delim = contentType.indexOf(';')
        return (delim < 1) ? contentType : contentType[0..delim-1]
    }

    static String parseCharsetParamFromContentType(String contentType) {
        int start = contentType.toLowerCase().indexOf("charset=")
        if (start == -1) return null
        String charset = contentType.substring(start)
        int end = charset.indexOf(' ')
        if (end != -1) charset = charset.substring(0, end)
        return charset.split("=")[1]
    }

    static String mapToURLEncodedString(params) {
        if (!params || !(params instanceof Map)) {
            return null
        }
        def encodedList = []
        for (entry in params) {
            if (entry.value != null && entry.value instanceof List) {
                for (item in entry.value) {
                    encodedList << urlEncodePair(entry.key, item)
                }
                continue
            }
            encodedList << urlEncodePair(entry.key, entry.value)
        }
        return encodedList.join('&')
    }

    private static String urlEncodePair(key, value) {
        if (!key) return ""
        value = value ?: ""
        return "${URLEncoder.encode(key.toString())}=${URLEncoder.encode(value.toString())}"
    }

}
