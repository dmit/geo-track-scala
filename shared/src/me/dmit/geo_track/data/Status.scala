package me.dmit.geo_track.data

import io.chrisdavenport.fuuid.FUUID
import io.chrisdavenport.fuuid.circe.*
import io.circe.{Codec, Decoder, Encoder}
import lspace.types.geo.Point
import me.dmit.geo_track.util.Codecs.squantsCodecIn
import squants.motion.{KilometersPerHour, Velocity}
import squants.space.{Angle, Degrees}

import java.time.Instant
import java.util.UUID
import scala.util.Try

// TODO: Configure serialization to encode `Some[T](val)` as plain `val`, and deserialization to
// support the reverse, as well as converting `null` and absence of value to `None`.
// See: https://github.com/circe/circe/pull/1800 for upstream work that should makes this easier.
final case class Status(
  sourceId: SourceId,
  timestamp: Instant,
  position: Option[Point] = None,
  bearing: Option[Angle] = None,
  speed: Option[Velocity] = None,
) derives Codec.AsObject

object Status {
  // java.time
  given instantAsEpochSeconds: Codec[Instant] = Codec.from(
    Decoder.decodeLong.emapTry { secs => Try(Instant.ofEpochSecond(secs)) },
    Encoder.encodeLong.contramap[Instant](_.toEpochMilli / 1000),
  )

  // Squants
  given angleAsDegrees: Codec[Angle]         = squantsCodecIn(Degrees)
  given velocityAsKmPerHour: Codec[Velocity] = squantsCodecIn(KilometersPerHour)
}
