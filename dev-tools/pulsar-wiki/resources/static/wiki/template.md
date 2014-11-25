%%% prettify install=true
...
%%%

# Module - group / module-dir

## Description
TBD

## Module exemplifies
* TBD

## Architectual considerations
TBD

## Usage instructions
TBD

## Code walkthrough
### APIs
%%% prettify lang=java linenums=true file=../examples/1-chatter-base/chatter-api/src/se/dse/pulsarbyexample/chatter/api/ChatterServer.java title=examples/1-chatter-base/chatter-api/src/.../chatter/api/ChatterServer.java
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

### Module 
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