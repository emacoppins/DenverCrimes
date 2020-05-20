package it.polito.tdp.crimes.model;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {

	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;

	public Model() {

		dao = new EventsDao();

		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

	}

	public void creaGrafo(String categoria, Month month) {
		for (Event e : dao.getReatiPerCategoria(categoria, month)) {
			grafo.addVertex(e);
		}
	}

	public List<String> popolaCombo() {
		return dao.eventsCategories();
	}

}
