package pl.wrona.iot.gtfs.collector.cyprus;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@Testcontainers
class CyprusServiceIntegrationTest {

    private static WireMockServer cyprusWireMockServer;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

    @Autowired
    private CyprusService cyprusService;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("gtfs.collector.feeds.cyprus.url", cyprusWireMockServer::baseUrl);
    }

    @BeforeAll
    public static void setUp() {
        cyprusWireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig()
                .port(8010));
        cyprusWireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        cyprusWireMockServer.stop();
    }

    @Test
    void shouldGenerateGtfsForCyprusPublicTransport() {
        //when
        cyprusWireMockServer.stubFor(get(urlEqualTo("/api/services/app/Gtfs/GetRoutesDistinct?agencyId=10"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withHeader(HttpHeaders.CONTENT_LENGTH, "21678")
                        .withStatus(HttpStatus.OK.value())
                        .withBody("""
                                {
                                  "result": {
                                    "langLocale": null,
                                    "routes": [
                                      {
                                        "routeId": 20260011,
                                        "agencyId": 10,
                                        "shortName": "401",
                                        "sortOrder": 401,
                                        "longName": "Mari - Vasiliko - Zygi - Kalavasos - Tochni - Choirokoitia - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "4ACFFF",
                                        "textColor": "000000",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 278
                                      },
                                      {
                                        "routeId": 20270011,
                                        "agencyId": 10,
                                        "shortName": "402",
                                        "sortOrder": 402,
                                        "longName": "Maroni - Psematismenos - Choirokoitia - Kofinou - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "E151C5",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 282
                                      },
                                      {
                                        "routeId": 20280011,
                                        "agencyId": 10,
                                        "shortName": "403",
                                        "sortOrder": 403,
                                        "longName": "Odou - Melini - Ora - Lagia - Choirokoitia - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "39BB41",
                                        "textColor": "000000",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 284
                                      },
                                      {
                                        "routeId": 20290011,
                                        "agencyId": 10,
                                        "shortName": "404",
                                        "sortOrder": 404,
                                        "longName": "Agioi Vavatsinias - Lagia - Vavla - Kato Drys - Skarinou - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "632401",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 286
                                      },
                                      {
                                        "routeId": 20300011,
                                        "agencyId": 10,
                                        "shortName": "405",
                                        "sortOrder": 405,
                                        "longName": "Vavatsinia - Pano Lefkara - Kato Lefkara - Skarinou - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "0FF804",
                                        "textColor": "000000",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 288
                                      },
                                      {
                                        "routeId": 20320011,
                                        "agencyId": 10,
                                        "shortName": "406",
                                        "sortOrder": 406,
                                        "longName": "Tourist Area Pentaschinos - Agios Theodoros - Kofinou St.",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "40110F",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 294
                                      },
                                      {
                                        "routeId": 20330011,
                                        "agencyId": 10,
                                        "shortName": "407",
                                        "sortOrder": 407,
                                        "longName": "Larnaca St. - Airport - Meneou - Kiti - Mazotos - Tourist Area Pentaschinos - Zygi - Vasiliko ",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "399001",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 296
                                      },
                                      {
                                        "routeId": 20340011,
                                        "agencyId": 10,
                                        "shortName": "408",
                                        "sortOrder": 408,
                                        "longName": "Larnaca Station - Alethriko - Anglisides - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "49580D",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 298
                                      },
                                      {
                                        "routeId": 20350011,
                                        "agencyId": 10,
                                        "shortName": "408B",
                                        "sortOrder": 408,
                                        "longName": "Kofinou Station - Anglisides - Vergina- Rizoelia",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "B94605",
                                        "textColor": "000000",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 300
                                      },
                                      {
                                        "routeId": 20360011,
                                        "agencyId": 10,
                                        "shortName": "409",
                                        "sortOrder": 409,
                                        "longName": "Larnaca St. - Lympia - Dali - Pera Chorio - Alampra St.",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "5A82A0",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 302
                                      },
                                      {
                                        "routeId": 20370011,
                                        "agencyId": 10,
                                        "shortName": "410",
                                        "sortOrder": 410,
                                        "longName": "Larnaca Station - Kalo Chorio - Agia Anna - Psevdas - Pyrga - Kornos - Delikipos",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "75610B",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 308
                                      },
                                      {
                                        "routeId": 20380011,
                                        "agencyId": 10,
                                        "shortName": "412",
                                        "sortOrder": 412,
                                        "longName": "Larnaca Station - Alethriko - Anglisides - Anafotia - Alaminos",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "001C9B",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 314
                                      },
                                      {
                                        "routeId": 20390011,
                                        "agencyId": 10,
                                        "shortName": "413",
                                        "sortOrder": 413,
                                        "longName": "Kivisili - Alethriko - Klavdia - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "0553E2",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 320
                                      },
                                      {
                                        "routeId": 20400021,
                                        "agencyId": 10,
                                        "shortName": "414",
                                        "sortOrder": 414,
                                        "longName": "Larnaca Station - Alethriko-Aplanta-Anglisides-Menogeia-Politiko A- Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF0080",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 328
                                      },
                                      {
                                        "routeId": 20410011,
                                        "agencyId": 10,
                                        "shortName": "415",
                                        "sortOrder": 415,
                                        "longName": "Athienou - Avdellero - Aradippou - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "9D2B14",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 7,
                                        "gtfsGroupFk": null,
                                        "id": 330
                                      },
                                      {
                                        "routeId": 20010011,
                                        "agencyId": 10,
                                        "shortName": "417",
                                        "sortOrder": 417,
                                        "longName": "Larnaca Station - Aradippou",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "1C8600",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 216
                                      },
                                      {
                                        "routeId": 20040011,
                                        "agencyId": 10,
                                        "shortName": "418",
                                        "sortOrder": 418,
                                        "longName": "Larnaca Station - Lividia - Kellia - Troulloi",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "33CC33",
                                        "textColor": "000000",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 222
                                      },
                                      {
                                        "routeId": 20100021,
                                        "agencyId": 10,
                                        "shortName": "418L",
                                        "sortOrder": 418,
                                        "longName": "Larnaca Station - Chrysopolitissa - Ag. Anargyroi-Livadia-Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "004000",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 244
                                      },
                                      {
                                        "routeId": 20050011,
                                        "agencyId": 10,
                                        "shortName": "419",
                                        "sortOrder": 419,
                                        "longName": "Larnaca Station - Oroklini",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF33CC",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 224
                                      },
                                      {
                                        "routeId": 20070011,
                                        "agencyId": 10,
                                        "shortName": "420",
                                        "sortOrder": 420,
                                        "longName": "Larnaca Station - Kamares - Kalo Chorio",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "D6AA80",
                                        "textColor": "000000",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 229
                                      },
                                      {
                                        "routeId": 20080021,
                                        "agencyId": 10,
                                        "shortName": "421",
                                        "sortOrder": 421,
                                        "longName": "Larnaca Station - Kamares - Free Trade Zone - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF80C0",
                                        "textColor": "000000",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 237
                                      },
                                      {
                                        "routeId": 20090011,
                                        "agencyId": 10,
                                        "shortName": "422",
                                        "sortOrder": 422,
                                        "longName": "Larnaca St. -New Hospital - Vergina ",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "CC6600",
                                        "textColor": "000000",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 238
                                      },
                                      {
                                        "routeId": 20110011,
                                        "agencyId": 10,
                                        "shortName": "423",
                                        "sortOrder": 423,
                                        "longName": "Larnaca Station- Faneromeni - Drosia Area -Mall",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF80FF",
                                        "textColor": "000000",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 245
                                      },
                                      {
                                        "routeId": 20120011,
                                        "agencyId": 10,
                                        "shortName": "424",
                                        "sortOrder": 424,
                                        "longName": "Larnaca St. -  Finikoudes-Old Hospital-CTO - Pyla",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "94462C",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 247
                                      },
                                      {
                                        "routeId": 20150011,
                                        "agencyId": 10,
                                        "shortName": "425",
                                        "sortOrder": 425,
                                        "longName": "Dekeleia - C.T.O. - Larnaca Station - Foinikoudes- Makenzy - Airport",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "791D6E",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 254
                                      },
                                      {
                                        "routeId": 20160011,
                                        "agencyId": 10,
                                        "shortName": "426",
                                        "sortOrder": 426,
                                        "longName": "Larnaca Station - Kalogera - E.A.C. - New Hospital - Kokkines - Tsiakkilero - Aradippou",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "DF32FF",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 260
                                      },
                                      {
                                        "routeId": 20170011,
                                        "agencyId": 10,
                                        "shortName": "427",
                                        "sortOrder": 427,
                                        "longName": "Larnaca St. - Harbour - Fire Station - Old Hospital - Foinikoudes - New Hospital - Cineplex - Faneromeni - Larnaca St.",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "0000A0",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 262
                                      },
                                      {
                                        "routeId": 20180011,
                                        "agencyId": 10,
                                        "shortName": "428",
                                        "sortOrder": 428,
                                        "longName": "Larnaca St. - Tsiakkilero- Aradippou - Pascal Area",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "8080FF",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 8,
                                        "gtfsGroupFk": null,
                                        "id": 264
                                      },
                                      {
                                        "routeId": 20190011,
                                        "agencyId": 10,
                                        "shortName": "429",
                                        "sortOrder": 429,
                                        "longName": "Larnaca Station - Airport - Faros",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "400080",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 266
                                      },
                                      {
                                        "routeId": 20000011,
                                        "agencyId": 10,
                                        "shortName": "430",
                                        "sortOrder": 430,
                                        "longName": "Larnaca Station - Airport - Kiti - Pervolia",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "CCCC00",
                                        "textColor": "000000",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 211
                                      },
                                      {
                                        "routeId": 20030011,
                                        "agencyId": 10,
                                        "shortName": "431",
                                        "sortOrder": 431,
                                        "longName": "Larnaca Station-Airport -  Meneou - Dromolaxia",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "CC00E0",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 220
                                      },
                                      {
                                        "routeId": 20060011,
                                        "agencyId": 10,
                                        "shortName": "432",
                                        "sortOrder": 432,
                                        "longName": "Larnaca Station - Airport - Dromoloxia - Kiti - Tersefanou",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF0000",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 227
                                      },
                                      {
                                        "routeId": 20500011,
                                        "agencyId": 10,
                                        "shortName": "440",
                                        "sortOrder": 440,
                                        "longName": "Larnaca St. - Mackenzie - Mall - Larnaca St.",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF8000",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 352
                                      },
                                      {
                                        "routeId": 20210011,
                                        "agencyId": 10,
                                        "shortName": "442",
                                        "sortOrder": 442,
                                        "longName": "Nightly Larnaca Station - Livadia - Kellia - Troulloi",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "00FFFF",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 269
                                      },
                                      {
                                        "routeId": 20220011,
                                        "agencyId": 10,
                                        "shortName": "443",
                                        "sortOrder": 443,
                                        "longName": "Nightly Larnaca St. -Harbour - Fire St. - Old Hospital -Foinikoudes - New Hospital - Cineplex - Faneromeni - Larnaca St.",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "808080",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 271
                                      },
                                      {
                                        "routeId": 20230011,
                                        "agencyId": 10,
                                        "shortName": "444",
                                        "sortOrder": 444,
                                        "longName": "Nightly Larnaca St. - Mitropoli - Trifilia - Tsiakkilero- G.S.Z. Stadium- Vergina- Aradippou ",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "804040",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 272
                                      },
                                      {
                                        "routeId": 20240011,
                                        "agencyId": 10,
                                        "shortName": "445",
                                        "sortOrder": 445,
                                        "longName": "Nightly Oroklini - Pyla - Κ.Ο.Τ - Foinikoudes - Makenzy - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FE5841",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 274
                                      },
                                      {
                                        "routeId": 20250011,
                                        "agencyId": 10,
                                        "shortName": "446",
                                        "sortOrder": 446,
                                        "longName": "Nightly Larnaca Station - Makenzy - Dromoloxia - Tersefanou - Kiti - Pervolia",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "66CCFF",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 276
                                      },
                                      {
                                        "routeId": 20420011,
                                        "agencyId": 10,
                                        "shortName": "448",
                                        "sortOrder": 448,
                                        "longName": "\\"Nightly Kofinou Station - Larnaca Station (Connection With Routes: 449 450 451)\\"",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "5BD312",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 336
                                      },
                                      {
                                        "routeId": 20430011,
                                        "agencyId": 10,
                                        "shortName": "449",
                                        "sortOrder": 449,
                                        "longName": "Nightly Vavatsinia - Pano Lefkara - Kato Lefkara - Kato Drys - Skarinou - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "DFB27F",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 338
                                      },
                                      {
                                        "routeId": 20440011,
                                        "agencyId": 10,
                                        "shortName": "450",
                                        "sortOrder": 450,
                                        "longName": "Nightly Kalavasos - Tochni - Psematismenos - Maroni - Agios Theodoros - Kofinou - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "990033",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 340
                                      },
                                      {
                                        "routeId": 20450011,
                                        "agencyId": 10,
                                        "shortName": "451",
                                        "sortOrder": 451,
                                        "longName": "Nightly Odou - Melini - Agioi Vavatsinias - Ora - Lagia - Vavla - Choirokoitia - Kofinou Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF8080",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 342
                                      },
                                      {
                                        "routeId": 20460011,
                                        "agencyId": 10,
                                        "shortName": "452",
                                        "sortOrder": 452,
                                        "longName": "Nightly Alaminos - Anafotia - Aplanta - Kivisili - Alethriko - Klavdia - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "008000",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 344
                                      },
                                      {
                                        "routeId": 20470011,
                                        "agencyId": 10,
                                        "shortName": "454",
                                        "sortOrder": 454,
                                        "longName": "Nightly Athienou - Avdellero - Aradippou - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "FF99FF",
                                        "textColor": "000000",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 346
                                      },
                                      {
                                        "routeId": 20480011,
                                        "agencyId": 10,
                                        "shortName": "455",
                                        "sortOrder": 455,
                                        "longName": "Nightly Kornos - Pyrga - Mosfiloti - Psevdas - Agia Anna - Kalo Chorio - Larnaca Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "0080FF",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 5,
                                        "gtfsGroupFk": null,
                                        "id": 348
                                      },
                                      {
                                        "routeId": 20490011,
                                        "agencyId": 10,
                                        "shortName": "456",
                                        "sortOrder": 456,
                                        "longName": "Nightly Vasiliko - Mari - Zygi - Tourist Area Pentaschinos - Mazotos - Kiti - Meneou - Airport - Larnaka Station",
                                        "description": null,
                                        "routeType": 3,
                                        "routeColor": "C53B66",
                                        "textColor": "FFFFFF",
                                        "gtfsGroupId": 9,
                                        "gtfsGroupFk": null,
                                        "id": 350
                                      }
                                    ],
                                    "groups": [
                                      {
                                        "name": "Urban Routes",
                                        "groupId": 800,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 8
                                      },
                                      {
                                        "name": "Rural Routes",
                                        "groupId": 700,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 7
                                      },
                                      {
                                        "name": "Routes (L)",
                                        "groupId": 1300,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 13
                                      },
                                      {
                                        "name": "Null Routes",
                                        "groupId": 1000,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 10
                                      },
                                      {
                                        "name": "Night Routes",
                                        "groupId": 500,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 5
                                      },
                                      {
                                        "name": "Assisting Routes",
                                        "groupId": 600,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 6
                                      },
                                      {
                                        "name": "Airport Routes",
                                        "groupId": 900,
                                        "gtfsAgencyId": 2,
                                        "gtfsAgencyFk": {
                                          "agencyId": 10,
                                          "agencyName": "Larnaca / Λάρνακα",
                                          "agencyUrl": "https://publictransport.com.cy",
                                          "agencyTimezone": "Asia/Nicosia",
                                          "agencyLang": "en",
                                          "isActive": true,
                                          "id": 2
                                        },
                                        "id": 9
                                      }
                                    ]
                                  },
                                  "targetUrl": null,
                                  "success": true,
                                  "error": null,
                                  "unAuthorizedRequest": false,
                                  "__abp": true
                                }
                                                                
                                """)));

        cyprusWireMockServer.stubFor(get(urlEqualTo("/api/services/app/Gtfs/GetRoutesDistinct?agencyId=10"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("cyprus/GetRoutesFull.json")));

        cyprusService.generateGtfs();
    }

}