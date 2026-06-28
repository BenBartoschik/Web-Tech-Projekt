# Web-Tech-Projekt

Budget-Planungs-App
Eine webbasierte Anwendung zur persönlichen Finanzverwaltung. Nutzer können ihre Einnahmen und Ausgaben tracken, Transaktionen kategorisieren und Sparziele über eine Wunschliste verwalten.
Features

Transaktionen erstellen, anzeigen und verwalten
Kategorien für Ausgaben anlegen
Wunschliste mit Sparzielen und Zieldatum
Benutzerprofile mit Einkommens- und Fixkostenübersicht

## API

### POST /transactions
Erstellt eine neue Transaktion. Pflichtfelder:
- `title` – darf nicht leer sein
- `amount` – muss größer als 0 sein

Bei ungültigen Werten antwortet die API mit **HTTP 400** und einer Fehlermeldung als Text.

Beispiel-Request:
```json
{
  "title": "Miete",
  "amount": 850.0,
  "category": "Rent",
  "owner": "user@example.com"
}
```

### GET /transactions?owner={email}
Gibt alle Transaktionen des angegebenen Owners zurück. Ohne `owner`-Parameter wird eine leere Liste zurückgegeben.

### GET /transactions/sum?owner={email}
Gibt die Summe aller `amount`-Werte eines Owners zurück. Ohne `owner`-Parameter antwortet die API mit **HTTP 400**.

Beispiel-Response:
```
1450.5
```

---

von:
Alexander Kalinin (s0582807)
Ben B. Bartoschik (s0600105)