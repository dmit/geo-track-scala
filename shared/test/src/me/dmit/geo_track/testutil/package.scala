package me.dmit.geo_track

package object testutil {
  object EitherOps:
    extension [L <: Throwable, R](e: Either[L, R])
      def getOrThrow: R = e match
        case Left(err) => throw err
        case Right(x)  => x
}
