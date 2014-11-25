%%% prettify install=true
...
%%%


# Module - 1-chatter-base / chatter-api

## Description
The Chatter API module specifies the interfaces and value objects used by the other Chatter modules. This module only contains the API.

## Module exemplifies
* Simple API design
* Versioning of API and module
* Separating APIs from implementation modules

## Architectual considerations
By separating the API into a separate module a server and client is decoupled in complie time. This also means that the server and client lifecycles are also decoupled in runtime, i.e the server may be upgraded without the client having to restart. Instead the client can reconnect to the services provided by the server when it becomes available.

## Usage instructions
The Chatter API itself does not contain any functionality or implementation. To use the API you need an implementation, like [chatter-server](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index/pulsarbyexample.chatter.server/) and a user interface (client), like the command line shell [chatter-shellui](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index/pulsarbyexample.chatter.shellui/) or [chatter-htmlui](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index/pulsarbyexample.chatter.htmlui/), an HTML client.

## Code walkthrough
### The ChatterServer API
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/api/ChatterServer.java title=examples/1-chatter-base/chatter-api/src/.../chatter/api/ChatterServer.java
...
%%%
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/api/ChatterMessage.java title=examples/1-chatter-base/chatter-api/src/.../chatter/api/ChatterMessage.java
...
%%%

### Version of API
The API version is defined in the `package-info.java` file in the package directory.
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/api/package-info.java title=examples/1-chatter-base/chatter-api/src/.../chatter/api/package-info.java
...
%%%

### Version of the module
The module version is defined in the `package-info.java` file in the module root package (usually where the activator class is). Note, here the `@PulsarModule` annotation is used instead of the `@Version` annotation.
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/package-info.java title=examples/1-chatter-base/chatter-api/src/.../chatter/package-info.java
...
%%%

### Module Activator
Since we are only defining an API this module doesn't have to do anything in it's activator.
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/Activator.java title=examples/1-chatter-base/chatter-api/src/.../chatter/Activator.java
...
%%%

## Excersices
1. What would be the new versions of the api package and module if we add a third parameter to the `chat()` method? What if we then add a a new method to the interface?
2. (Advanced) Extend the API with listener support, making it possible for clients to receive push notifications instead of just polling for new messages. Then implement this functionality in chatter-server and update the chatter-shellui to use push notifications instead of polling.

## References


## Links
* [Wiki top](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index)