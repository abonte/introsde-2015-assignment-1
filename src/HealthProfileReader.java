import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HealthProfileReader {

	Document doc;
	XPath xpath;

	/**
	 * Load the xml in memory
	 */
	public void loadXML() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people.xml");
		getXPathObj();
	}
	
	/**
	 * creating xpath object
	 */
	public XPath getXPathObj() {

		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}

	//1. Use xpath to implement methods like getWeight and getHeight
	/**
	 * Given the first name and last name of a person, it returns
	 * a Node object containing the node weight of the person.
	 * 
	 * @param firstname 
	 * @param lastname
	 * @return node the Node object weight
	 */
	public Node getWeight(String firstname, String lastname) throws XPathExpressionException {

		XPathExpression expr = xpath.compile("/people/person[firstname='" + firstname + "' and lastname='" + lastname + "']/healthprofile/weight");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}
	
	/**
	 * Given the first name and last name of a person, it returns
	 * a Node object containing the node height of the person.
	 * 
	 * @param firstname 
	 * @param lastname
	 * @return node the Node object height
	 */
	public Node getHeight(String firstname, String lastname) throws XPathExpressionException {

		XPathExpression expr = xpath.compile("/people/person[firstname='" + firstname + "' and lastname='" + lastname + "']/healthprofile/height");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}

	//2. Make a function that prints all people in the list with detail
	/**
	 * Print all the people store in the file people.xml. For each person
	 * are printed all the details. 
	 */
	public void printPeople() throws XPathExpressionException {
		
		XPathExpression expr = xpath.compile("/people/person");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result; 					//List of all node with name 'person'
		/*for (int i = 0; i < nodes.getLength(); i++) {
 	        System.out.println(nodes.item(i).getTextContent());
 	    }*/
		for (int i = 0; i < nodes.getLength(); i++) {
			System.out.println("========================================");
			System.out.println("ID: "+ nodes.item(i).getAttributes().item(0).getTextContent() );
			NodeList person= nodes.item(i).getChildNodes();   //get all child nodes of the parent node person
			Element nelement = (Element) person;
			System.out.println("Firstname is: " + nelement.getElementsByTagName("firstname").item(0).getTextContent());
			System.out.println("Lastname is: " + nelement.getElementsByTagName("lastname").item(0).getTextContent());
			System.out.println("Birthdate is: " + nelement.getElementsByTagName("birthdate").item(0).getTextContent());
			System.out.println("Healthprofile");
			System.out.println("	Height is: " + nelement.getElementsByTagName("height").item(0).getTextContent());
			System.out.println("	Weight is: " + nelement.getElementsByTagName("weight").item(0).getTextContent());
			System.out.println("	Bmi is: " + nelement.getElementsByTagName("bmi").item(0).getTextContent());
			System.out.println("");	
		} 
	}

	//3. A function that accepts id as parameter and prints the HealthProfile of the person with that id
	/**
	 * Given an id of a person, the method return the node corresponding
	 * to the person whit that id.
	 * 
	 * @param id id of the person stored in the people.xml
	 * @return node contain the person
	 */
	public Node getPersonById(Long id) throws XPathExpressionException {
		
		String id_s = String.format("%04d", id);
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_s + "']");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		return node;
	}

	//4. A function which accepts a weight and an operator (=, > , <) as parameter
	//and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).
	/**
	 * Given a weight and an operator, return all people that fulfilling the condition
	 * @param weight decimal values of the weight
	 * @param operator =,<,> express the condition
	 * @return nodes NodeList containing the people
	 */
	public NodeList getPersonByWeight(Double weight, String operator) throws XPathExpressionException{

		XPathExpression expr = xpath.compile("//healthprofile[weight " + operator + "'" + weight + "']/..");
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		HealthProfileReader test = new HealthProfileReader();
		test.loadXML();

		Node node;
	
		int argCount = args.length;
		if (argCount == 0) {
			System.out.println("I cannot execute any action.");
		} else {
			String method = args[0];
			if (method.equals("printPeople")) {
				test.printPeople();
			} else if (method.equals("printPerson")) {
				Long personId = Long.parseLong(args[1]);
				node = test.getPersonById(personId);
				if (node != null){
					System.out.println("Node name: " + node.getNodeName());
					System.out.println("My childs text contents :" + node.getTextContent());
				}
			}  else if (method.equals("getPersonByWeight")) {
				Double weight = Double.parseDouble(args[1]);
				String operator = args[2];
				NodeList nodes = test.getPersonByWeight(weight,operator);
				for (int i = 0; i < nodes.getLength(); i++) {
					System.out.println(nodes.item(i).getTextContent());
				}
			} else {
				System.out.println("The system did not find the method '"+method+"'");
			}
		}

		/*
		//example of the use of getHeight and getWeight (instruction 1 based on Lab 3)
		String firstname = "Cleo";
		String lastname = "Hammes";
		
		//getting height of a person
		Node node = test.getHeight(firstname, lastname);
		if (node != null){       	
			System.out.println("Node name: " + node.getNodeName());
			System.out.println("My childs text contents :" + node.getTextContent());
		}

		//getting weight of a person
		node = test.getWeight(firstname, lastname);
		if (node != null){
			System.out.println("Node name: " + node.getNodeName());
			System.out.println("My childs text contents :" + node.getTextContent());
		}
		*/
		
	}
}