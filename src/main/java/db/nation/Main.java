//	MILESTONE 1
//	Creare un nuovo database in DBeaver e importare lo schema inallegato.Scrivere una query SQL che restituisca la lista di tutte le nazionimostrando nome, id, nome della regione e nome del continente, ordinataper nome della nazione.
//	MILESTONE 2
//	Creare un progetto Maven, configurato in modo da potersi connettere adun database MySQL.Nel progetto creare un programma che esegua la query della Milestone1 e stampi a video il risultato.
//	MILESTONE 3
//	Modificare il programma precedente per fare in modo che un utentepossa inserire una ricerca e filtrare i risultati:- chiedere all’utente di inserire una stringa di ricerca da terminale- usare quella stringa come parametro aggiuntivo della query inmodo che i risultati vengano filtrati con un contains (ad esempio sel’utente cerca per “ita”, il risultato della query conterrà sia Italy che Mauritania
//	BONUS
//	Dopo aver stampato a video l’elenco delle country, chiedere all’utente diinserire l’id di una delle country.Sulla base di quell’id eseguire ulteriori ricerche su database, cherestituiscano:● tutte le lingue parlate in quella country● le statistiche più recenti per quella country

package db.nation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

	public class Main {
		public static void main(String[] args) throws SQLException{

			 String url = "jdbc:mysql://localhost:3306/db-nations";
			 String user = "root";
			 String password = "root";
			 String input = "";
			 Scanner s=new Scanner(System.in);			 
			 System.out.println("Inserisci il nome del paese o parte di esso");
			 input = (s.nextLine());
			  try (Connection con = DriverManager.getConnection(url, user, password)){
				  
				  String sql="select c.name, c.country_id , r.name , c2.name  from countries c \r\n"
				  		+ "join regions r on c.region_id = r.region_id \r\n"
				  		+ "join continents c2 on c2.continent_id = r.continent_id \r\n"
				  		+ "where c.name like ?"
				  		+ "order by c.name asc";
				 
				  try(PreparedStatement ps=con.prepareStatement(sql)) {
					  
					  ps.setString(1, "%"+input+"%");
					  
					  try(ResultSet rs = ps.executeQuery()) {
						  
						  System.out.println("");
						  while (rs.next()) { //se c'è qualcosa da leggere
							  System.out.println(
									  rs.getInt("c.country_id") +"\t"+
									  rs.getString("c.name") +"\t\t"+
									  rs.getString("r.name") +"\t"+
									  rs.getString("c2.name"));
						 					  
						  } 
					  }
					  
				  }
			  } catch (SQLException ex) {
			     ex.printStackTrace();
			  }

			s.close();		
		}

}
