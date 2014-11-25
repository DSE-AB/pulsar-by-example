# 4-chatter-persistence / chatter-store

## Description
This module provides an alternative storage service according to the inteface `ChatterPersistence` as defined by `pulsarbyexample.chatter.server`.

## Module exemplifies
* Using module start level
* Using DatabaseAccess
* Releasing resources
* Providing a service

## Usage instructions
Disable the built in memory store in the module `pulsarbyexample.chatter.server` using [`AdminConsole/Configure`](/pulsar/ConfigEditor/ConfigEditor.html?i_moduleSymbolicName=pulsarbyexample.chatter.server). The `chatter-store` will then automatically be picked up as the storage provider.

## References
[Derby DB](http://db.apache.org/derby/)

## Links
* [Wiki top](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index)