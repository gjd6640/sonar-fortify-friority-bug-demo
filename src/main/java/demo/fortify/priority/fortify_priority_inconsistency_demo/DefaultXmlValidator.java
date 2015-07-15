package demo.fortify.priority.fortify_priority_inconsistency_demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public final class DefaultXmlValidator  {

  private URL schemaToValidateAgainst; // constructor that sets this removed to make this example more concise

  public void validate(final String request) {
    try {
      // Create a SchemaFactory capable of understanding WXS schemas.
      final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

      // Load a WXS schema, represented by a Schema instance.
      final Schema schema = factory.newSchema(schemaToValidateAgainst);

      // Create a Validator object, which can be used to validate
      // an instance document.
      final Validator validator = schema.newValidator();

      // Validate the DOM tree.
      final Document document = parseDocument(request);
      validator.validate(new DOMSource(document));
    } catch (Exception e) {
      throw new IllegalStateException("Error while attemping to validate request [" + request + "]", e);
    }
  }

  private Document parseDocument(final String xmlString)
      throws ParserConfigurationException, SAXException, IOException {
    // Parse an XML document into a DOM tree.
    final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);

    final DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();

    final ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(Charset.forName("UTF-8")));
    final Document document = parser.parse(inputStream);
    inputStream.reset();
    inputStream.close();

    return document;
  }

}
