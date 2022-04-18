package com.holo.architecture.logger.klog

import android.util.Log
import com.holo.architecture.logger.KLog
import com.holo.architecture.logger.KLogUtil.isEmpty
import com.holo.architecture.logger.KLogUtil.printLine
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Created on 2020/8/11.
 * @author Holo
 */
internal object XmlLog {

    fun printXml(tag: String?, xml: String?, headString: String) {
        var xml = xml
        if (xml != null) {
            xml = formatXML(xml)
            xml = """
                $headString
                $xml
                """.trimIndent()
        } else {
            xml = headString + KLog.NULL_TIPS
        }
        printLine(tag, true)
        val lines = xml.split(KLog.LINE_SEPARATOR!!).toTypedArray()
        for (line in lines) {
            if (!isEmpty(line)) {
                Log.d(tag, "║ $line")
            }
        }
        printLine(tag, false)
    }

    private fun formatXML(inputXML: String): String {
        return try {
            val xmlInput: Source = StreamSource(StringReader(inputXML))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
        } catch (e: Exception) {
            e.printStackTrace()
            inputXML
        }
    }
}