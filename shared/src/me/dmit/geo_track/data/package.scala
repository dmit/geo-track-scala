package me.dmit.geo_track

import cats.implicits.*
import io.chrisdavenport.fuuid.FUUID
import io.chrisdavenport.fuuid.circe.{fuuidDecoder, fuuidEncoder}
import io.circe.syntax.*
import io.circe.Codec

import java.time.Instant
import scala.util.Try

package object data {
  opaque type SourceId = FUUID;
  object SourceId:
    def fromString(s: String): Either[Throwable, SourceId] = FUUID.fromString(s)

  given Codec[SourceId] = Codec.from(fuuidDecoder, fuuidEncoder)
}
