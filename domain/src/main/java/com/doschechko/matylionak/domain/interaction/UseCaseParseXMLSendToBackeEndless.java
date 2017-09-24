package com.doschechko.matylionak.domain.interaction;

import android.app.Activity;
import android.content.res.AssetManager;

import com.doschechko.matylionak.data.entity.AnekdotData;
import com.doschechko.matylionak.data.net.Rest.RestService;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class UseCaseParseXMLSendToBackeEndless {

    private Activity activity;

    private ArrayList<AnekdotData> list = new ArrayList<>();

    public UseCaseParseXMLSendToBackeEndless(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<AnekdotData> parseXML() {
        AssetManager manager = activity.getAssets();
        SAXParserFactory factory = SAXParserFactory.newInstance();

        DefaultHandler handler = new DefaultHandler() {

            AnekdotData anekdot = null;
            private String thisElement = "";

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                thisElement = qName;
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                thisElement = "";
            }

            //Column1
            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (thisElement.equals("Column1")) {
                    anekdot = new AnekdotData();
                    anekdot.setBody(new String(ch, start, length));
                    list.add(anekdot);
                }
            }
        };

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(manager.open("backendless.xml"), handler);


        } catch (Exception e) {

        }

        RestService restService = RestService.getInstance();


        for (int i = 0; i < list.size(); i++) {
            restService.saveAnekdot(list.get(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
