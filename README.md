# Geo Track

A Scala 3 implementation of a toy geo-tracking service.

## Build

You will need [Mill](https://github.com/com-lihaoyi/mill) to build this project.

Build server jar:

```sh
mill server.assembly
```

## Development

Run tests:

```sh
mill _.test
```

Format code and run linter:

```sh
mill all "_.{fix, format}"
```

Check for dependency updates:

```sh
mill mill.scalalib.Dependency/showUpdates
```
