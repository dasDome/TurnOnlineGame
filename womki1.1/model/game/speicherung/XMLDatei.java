package game.speicherung;

import game.character.*;
import game.objekte.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLDatei implements iDatenzugriff3{
	@Override
	public void speichern(String dateiName,Properties p)throws IOException{
		if(!dateiName.endsWith("xml")){
			dateiName+=".xml";
		}
		try{
		JAXBContext context=JAXBContext.newInstance(Mensch.class,Ork.class,Zwerg.class,
				Kurzschwert.class,Langschwert.class,Schild.class,
				MantelDerUnsichtbarkeit.class,Siebenmeilenstiefel.class,VerfluchterUmhang.class);
		
		Marshaller m=context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(p.get("figur"), new FileWriter(dateiName));
		}catch(JAXBException e){
			throw new IOException(e.getMessage());
		}
	}
	@Override
	public Properties laden(String dateiName)throws IOException {
		String s="D:\\Uni\\Inf2\\praktikum\\womki1.1\\";
		if(dateiName==null){
			throw new IOException("Kein gültiger DateiName");
		}
		try{
		JAXBContext context=JAXBContext.newInstance(Mensch.class,Ork.class,Zwerg.class,
				Kurzschwert.class,Langschwert.class,Schild.class,
				MantelDerUnsichtbarkeit.class,Siebenmeilenstiefel.class,VerfluchterUmhang.class);
		Unmarshaller um=context.createUnmarshaller();
		Properties p= new Properties();
		p.put("Figur",(Figur)um.unmarshal(new FileReader(s+dateiName)));
		return p;
		}catch(JAXBException e){
			throw new IOException(e.getMessage());
		}
	}

}
