package htw.webtech.projektname;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Deaktiviert: dieser Test prüft nur ob der Spring-Context startet,
// braucht dafür aber eine laufende PostgreSQL-DB (DATABASE_URL etc.).
// Ohne DB schlägt er fehl — auf CI und lokal ohne DB nicht ausführbar.
@Disabled
@SpringBootTest
class ProjektnameApplicationTests {

	@Test
	void contextLoads() {
	}

}
