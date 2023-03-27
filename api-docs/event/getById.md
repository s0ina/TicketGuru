# Näytä tapahtuma eventId:llä

**URL**: `/events/:pk`

**Metodi**: `GET`

**Vaadittu rooli**: Admin tai Myyjä

## Vastauksen paluukoodit

**Koodit**:

`200 OK`

`404 NOT FOUND`

> `"message": "Annetulla id:llä ei löytynyt tapahtumaa"`

## Esimerkkivastaus:

**Polku**: `BASE_URL/events/1`

```json
{
  "eventId": 1,
  "eventName": "Testitapahtuma",
  "description": "Tapahtuman kuvaus",
  "startTime": "2023-02-14T16:00:00",
  "endTime": "2023-02-14T18:00:00",
  "amountTickets": 100,
  "presaleEnds": "2023-02-12T18:00:00",
  "cancelled": false,
  "venue": {
    "venueId": 1,
    "venueName": "tapahtumapaikka1",
    "venueDescription": "pieni paikka",
    "address": "Maitokatu 1",
    "postalCode": {
      "postalCode": "00100",
      "city": "Helsinki"
    }
  }
}
```
