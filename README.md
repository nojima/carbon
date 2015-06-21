# Carbon #

## SetUp ##

```sh
(Install bundler, node, npm and grunt-cli)
$ cd carbon
$ npm install
$ ./node_modules/dtsm/bin/dtsm install
$ bundle install
$ grunt fixts
$ grunt sass
$ grunt bundle
```

## Build & Run ##

```sh
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.


## Tests ##

```sh
$ grunt fixts
$ grunt bundle
$ grunt karma
```
