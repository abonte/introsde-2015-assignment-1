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
	 * Given the id of a person, it returns the weight of the person.
	 * @param id
	 * @return weight
	 */
	public String getWeight(String id) throws XPathExpressionException {
		
		String id_s = String.format("%04d", Integer.parseInt(id)); //Pad with zeros
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_s + "']/healthprofile/weight");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		String weight="";
		if (node != null){
			weight = node.getTextContent();
		}
		return weight;
	}

	/**
	 * Given the id of a person, it returns the height of the person.
	 * @param id
	 * @return height
	 */
	public String getHeight(String id) throws XPathExpressionException {
		
		String id_s = String.format("%04d", Integer.parseInt(id));
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_s + "']/healthprofile/height");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		String height="";
		if (node != null){
			height = node.getTextContent();
		}
		return height;
	}

	//2. Make a function that prints all people in the list with detail
	/**
	 * Return all the people store in the file people.xml.
	 * @return nodes NodeList containing all people 
	 */
	public NodeList getAllPeople() throws XPathExpressionException {
		
		XPathExpression expr = xpath.compile("/people/person");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result; 	//List of all node with name 'person'
		return nodes;
	}
	
	/**
	 * Print all details for each person in the NodeList nodes
	 * @param nodes 
	 */
	public void printListPeople(NodeList nodes) throws XPathExpressionException{
		for (int i = 0; i < nodes.getLength(); i++) {
			printPerson(nodes.item(i));
		}
	}
	
	/**
	* Print details of a person
	* @param node a node that represent a person
	*/
	public void printPerson(Node node) throws XPathExpressionException{
		System.out.println("========================================");
		String id = node.getAttributes().item(0).getTextContent();
		System.out.println("ID: "+  id);
			NodeList person= node.getChildNodes();   //get all child nodes of the parent node person
			Element nelement = (Element) person;
			System.out.println("Firstname is: " + nelement.getElementsByTagName("firstname").item(0).getTextContent());
			System.out.println("Lastname is: " + nelement.getElementsByTagName("lastname").item(0).getTextContent());
			System.out.println("Birthdate is: " + nelement.getElementsByTagName("birthdate").item(0).getTextContent());
			System.out.println("Healthprofile");
			System.out.println("	Height is: " + getHeight(id));
			System.out.println("	Weight is: " + getWeight(id));
			System.out.println("	Bmi is: " + nelement.getElementsByTagName("bmi").item(0).getTextContent());
			System.out.println("");	
		}

	//3. A function that accepts id as parameter and prints the HealthProfile of the person with that id
	/**
	 * Given an id of a person, the method return the node corresponding
	 * to the person whit that id.
	 * @param id id of the person stored in the people.xml
	 * @return node contain the person
	 */
	public Node getPersonById(String id) throws XPathExpressionException {
		
		String id_s = String.format("%04d", Integer.parseInt(id));
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
	public NodeList getPeopleByWeight(Double weight, String operator) throws XPathExpressionException{

		XPathExpression expr = xpath.compile("//healthprofile[weight " + operator + "'" + weight + "']/..");
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		return nodes;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		HealthProfileReader test = new HealthProfileReader();
		test.loadXML();

		Node node;

		//the program take in input the name of the action
		//to perform and eventually other values
		int argCount = args.length;
		if (argCount == 0) {
			System.out.println("I cannot execute any action.");
		} else {
			String method = args[0];
			//parameter in input: getAllPeople
			if (method.equals("getAllPeople")) {
				NodeList nodes = test.getAllPeople();
				test.printListPeople(nodes);
			//parameters in input: getPersonById 5
			} else if (method.equals("getPersonById")) {
				String personId = args[1];
				node = test.getPersonById(personId);
				if (node != null){
					test.printPerson(node);
				} else {
					System.out.println("A person with id="+args[1]+" doesn't exist.");
				}
			//parameters in input: getPeopleByWeight 90 >
			} else if (method.equals("getPeopleByWeight")) {
				Double weight = Double.parseDouble(args[1]);
				String operator = args[2];
				NodeList nodes = test.getPeopleByWeight(weight,operator);
				test.printListPeople(nodes);

			} else {
				//when the arguments are not valid
				System.out.println("The system did not find the method '"+method+"'");
			}
		}
	}
}