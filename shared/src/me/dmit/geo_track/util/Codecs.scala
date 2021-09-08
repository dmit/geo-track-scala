package me.dmit.geo_track.util

import io.circe.{Codec, Decoder, Encoder}
import squants.{Quantity, UnitOfMeasure}

object Codecs {
  // From https://github.com/circe/circe/pull/1111#issuecomment-887676530
  def squantsCodecIn[Q <: Quantity[Q], T: Decoder: Encoder: Numeric](
    in: UnitOfMeasure[Q],
    toT: Double => T = identity,
  ): Codec[Q] =
    Codec.from(
      Decoder[T].map(in.apply),
      Encoder[T].contramap[Q](q => toT(q.to(in))),
    )
}
