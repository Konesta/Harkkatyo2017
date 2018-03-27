package paivakirja;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import paivakirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2017.04.26 14:19:52 // Generated by ComTest
 *
 */
public class PaivakirjaTest {



  // Generated by ComTest BEGIN
  /** testPaivakirja21 */
  @Test
  public void testPaivakirja21() {    // Paivakirja: 21
    Paivakirja paivakirja = new Paivakirja(); 
    assertEquals("From: Paivakirja line: 24", 0, paivakirja.getTapahtumia()); 
    Tapahtuma tapahtuma1 = new Tapahtuma(); 
    tapahtuma1.taytaTapahtuma(); 
    try {
    paivakirja.lisaa(tapahtuma1); 
    } catch (SailoException e) {
    System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage()); 
    }
    assertEquals("From: Paivakirja line: 33", 1, paivakirja.getTapahtumia()); 
    Tapahtuma tapahtuma2 = new Tapahtuma(); 
    tapahtuma2.taytaTapahtuma(); 
    try {
    paivakirja.lisaa(tapahtuma2); 
    } catch (SailoException e) {
    System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage()); 
    }
    assertEquals("From: Paivakirja line: 42", 2, paivakirja.getTapahtumia()); 
    assertEquals("From: Paivakirja line: 43", tapahtuma1, paivakirja.annaTapahtuma(0)); 
    assertEquals("From: Paivakirja line: 44", tapahtuma2, paivakirja.annaTapahtuma(1)); 
    paivakirja.setTiedosto("testifilu"); 
    try {
    paivakirja.tallenna(); 
    } catch (SailoException e) {
    System.out.println("Virhe tallennuksessa"); 
    }
    Tapahtuma tapahtuma3 = new Tapahtuma(); 
    tapahtuma3.taytaTapahtuma(); 
    try {
    paivakirja.lisaa(tapahtuma3); 
    } catch (SailoException e) {
    System.out.println("Ongelmia lisaamisen kanssa!" + e.getMessage()); 
    }
    assertEquals("From: Paivakirja line: 60", 3, paivakirja.getTapahtumia()); 
    try {
    paivakirja.lueTiedostosta("testifilu"); 
    } catch (SailoException e) {
    System.out.println("Virhe avauksessa " + e.getMessage()); 
    }
  } // Generated by ComTest END
}