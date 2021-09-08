package me.dmit.geo_track.data

import io.chrisdavenport.fuuid.FUUID
import lspace.types.geo.Point
import me.dmit.geo_track.testutil.EitherOps.*
import squants.motion.KilometersPerHour
import squants.space.Degrees
import utest.*

import java.time.{LocalDateTime, ZoneOffset}

object StatusTests extends TestSuite {
  val Minimal = Status(
    sourceId = SourceId.fromString("0aaec05a-0e7d-4fd5-abc0-0ba69e3cfe11").getOrThrow,
    timestamp = LocalDateTime.parse("2021-07-27T08:45:19").toInstant(ZoneOffset.ofHours(3)),
  )

  val Full = Minimal.copy(
    position = Some(Point(x = 24.745_278, y = 59.437_222)),
    bearing = Some(Degrees(1.234)),
    speed = Some(KilometersPerHour(15.0)),
  )

  val FullJson = """{
                   |  "sourceId": "0aaec05a-0e7d-4fd5-abc0-0ba69e3cfe11",
                   |  "timestamp": 1627364719,
                   |  "position": {
                   |    "x": 24.745278,
                   |    "y": 59.437222
                   |  },
                   |  "bearing": 1.234,
                   |  "speed": 15.0
                   |}""".stripMargin

  val MinimalJson = """{
                      |  "sourceId": "0aaec05a-0e7d-4fd5-abc0-0ba69e3cfe11",
                      |  "timestamp": 1627364719
                      |}""".stripMargin

  val tests = Tests {
    test("JSON serialization") {
      import io.circe.syntax.*

      val fullEncoded    = Full.asJson.spaces2
      val minimalEncoded = Minimal.asJson.spaces2

      assert(fullEncoded == FullJson)
      assert(minimalEncoded == MinimalJson)
    }

    test("JSON deserialization") {
      import io.circe.parser.decode

      val fullDecoded    = decode[Status](FullJson).getOrThrow
      val minimalDecoded = decode[Status](MinimalJson).getOrThrow

      assert(fullDecoded == Full)
      assert(minimalDecoded == Minimal)
    }
  }
}
