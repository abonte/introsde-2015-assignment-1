package peoplestore;

import peoplestore.generated.*;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

public class JAXBMarshaller {
	public void generateXMLDocument(File xmlDocument) throws DatatypeConfigurationException {
		try {
			
			//inizialization
			JAXBContext jaxbContext = JAXBContext.newInstance("peoplestore.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			peoplestore.generated.ObjectFactory factory = new peoplestore.generated.ObjectFactory();
			
			//create new objects
			PeopleType people = factory.createPeopleType();
			PersonType person = factory.createPersonType();
			List<PersonType> personList = people.getPerson();
			
			//create 3 person and add to personList
			person = createPerson(factory, "Andrea", "Colo", 12);
			personList.add(person);
			person = createPerson(factory, "Michele", "Bolo", 12);
			personList.add(person);
			person = createPerson(factory, "Carlo", "Dolo", 12);
			personList.add(person);
			
			//marshalling
			JAXBElement<PeopleType> peopleElement = factory.createPeople(people);
			marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument)); //marshalling into a file .xml
			marshaller.marshal(peopleElement, System.out);		// marshalling into the system default output

		} catch (IOException e) {
			System.out.println(e.toString());

		} catch (JAXBException e) {
			System.out.println(e.toString());

		}

	}
	
	/**
	 * Create a new person
	 * @param factory
	 * @param Firstname
	 * @param Lastname
	 * @param id
	 * @return person 
	 * @throws DatatypeConfigurationException
	 */
	public PersonType createPerson(peoplestore.generated.ObjectFactory factory, String Firstname, String Lastname, Integer id) throws DatatypeConfigurationException {
		PersonType person = factory.createPersonType();
		
		GregorianCalendar pinco_date = new GregorianCalendar(2000, 1,20,9,00);
		XMLGregorianCalendar xml_pincodate = DatatypeFactory.newInstance().newXMLGregorianCalendar(pinco_date);
		
		//set personal data 
		person.setFirstname(Firstname);
		person.setLastname(Lastname);
		person.setBirthdate(xml_pincodate);
		BigInteger id_pinco = new BigInteger("123");
		person.setId(id_pinco);
		
		//create data for healthprofile
		HealthDataType person_health_pinco = factory.createHealthDataType();
		
		//generate data
		GregorianCalendar pinco_lastupdate = new GregorianCalendar(2010, 1,23,9,00);
		XMLGregorianCalendar xml_pincolastupdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(pinco_lastupdate);
		BigDecimal pinco_weight = new BigDecimal("120");
		BigDecimal pinco_height = new BigDecimal("170");
		BigDecimal pinco_bmi = new BigDecimal("23");
		
		//set data in the healthprofile object
		person_health_pinco.setLastupdate(xml_pincolastupdate);
		person_health_pinco.setWeight(pinco_weight);
		person_health_pinco.setHeight(pinco_height);
		person_health_pinco.setBmi(pinco_bmi);
		person.setHealthprofile(person_health_pinco);
		
		return person;
	}
	
	public static void main(String[] argv) throws DatatypeConfigurationException {
		String xmlDocument = "peopleMarshallUnmarshall.xml";
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
		jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
	}
}
