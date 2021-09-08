import mill._, scalalib._, scalafmt._
import $ivy.`com.goyeau::mill-scalafix:0.2.5`
import com.goyeau.mill.scalafix.ScalafixModule

object Version {
  // Plugins
  val organizeImports = "0.5.0"

  // Dependencies
  val circe       = "0.14.1"
  val fuuid       = "0.8.0-M2"
  val lspaceTypes = "0.2.1"
  val scala       = "3.0.2"
  val squants     = "1.8.3"

  // Test dependencies
  val utest = "0.7.10"
}

trait BaseModule extends ScalaModule with ScalafixModule with ScalafmtModule {
  def scalaVersion = Version.scala
  def scalafixIvyDeps = Agg(
    ivy"com.github.liancheng::organize-imports:${Version.organizeImports}",
  )
}

object shared extends BaseModule {
  def ivyDeps = Agg(
    ivy"eu.l-space::types:${Version.lspaceTypes}",
    ivy"io.chrisdavenport::fuuid:${Version.fuuid}",
    ivy"io.chrisdavenport::fuuid-circe:${Version.fuuid}",
    ivy"io.circe::circe-core:${Version.circe}",
    ivy"org.typelevel::squants:${Version.squants}",
  )

  object test extends Tests with TestModule.Utest {
    def ivyDeps = Agg(
      ivy"io.circe::circe-parser:${Version.circe}",
      ivy"com.lihaoyi::utest:${Version.utest}",
    )
  }
}

object server extends BaseModule {
  def moduleDeps = Seq(shared)

  def ivyDeps = Agg(
    ivy"eu.l-space::types:${Version.lspaceTypes}",
    ivy"io.chrisdavenport::fuuid:${Version.fuuid}",
    ivy"io.chrisdavenport::fuuid-circe:${Version.fuuid}",
    ivy"io.chrisdavenport::fuuid-http4s:${Version.fuuid}",
    ivy"io.circe::circe-core:${Version.circe}",
    ivy"org.typelevel::squants:${Version.squants}",
  )
}
