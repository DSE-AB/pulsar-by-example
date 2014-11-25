Pulsar by Example
=================

Welcome
-------
Welcome to the Pulsar by Example collection. The purpose of this workspace is to exemplify how a modular architecture can be achieved with the Pulsar application server. Together the example modules form a very simple chat service called `Chatter`. 

### Example groups
The example collection consist of four module groups:
1. chatter-base - Here we create an API for the `Chatter` service in the `chatter-api` module, we implement the API in a `chatter-server` module. Finally we also create a command line client UI called `chatter-shellui` which uses the API to communicate with the server module.
2. chatter-html - The second level adds a very simple HTML UI which also comunicates with the server.
3. chatter-spa - In the third level we build a Single Page Application which communicated with the server using a REST version of the API. *TBD*
4. chatter-persistence - Finally we add database persistence for the chat history using an in memory database.

### Getting started
Start with installing the `chatter-api` using the `AdminConsole/Install` and then go to (the documentation for that module)[/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index/pulsarbyexample.chatter.api/].
