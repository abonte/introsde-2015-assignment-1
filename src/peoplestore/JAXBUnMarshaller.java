package peoplestore;

import peoplestore.generated.*;
import peoplestore.generated.HealthDataType;
import peoplestore.generated.PeopleType;
import peoplestore.generated.PersonType;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JAXBUnMarshaller {

	/**
	*/
	public void unMarshall(File xmlDocument) {
		try {
			//initialization and configuration
			JAXBContext jaxbContext = JAXBContext.newInstance("peoplestore.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(
					"people.xsd"));
			unMarshaller.setSchema(schema);
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			//un-marshalling
			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
					.unmarshal(xmlDocument);

			//print data retrived from XML
			PeopleType people = peopleElement.getValue();

			List<PersonType> personList = people.getPerson();
			//iterate on the person 
			for (int i = 0; i < personList.size(); i++) {

				PersonType person = (PersonType) personList.get(i);
				System.out.println("========================================");
				System.out.println("ID: "+  person.getId());
				System.out.println("Firstname: "+ person.getFirstname());
				System.out.println("Lastname: " + person.getLastname());
				System.out.println("Birthdate: " + person.getBirthdate());
				HealthDataType hp = person.getHealthprofile();
				System.out.println("Health Profile");
				System.out.println("	Weight: "+ hp.getWeight());
				System.out.println("	Height: " + hp.getHeight());
				System.out.println("	BMI: " + hp.getBmi());
				System.out.println("");
			}
			
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) {
		File xmlDocument = new File("people.xml");
		JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();

		jaxbUnmarshaller.unMarshall(xmlDocument);

	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}
