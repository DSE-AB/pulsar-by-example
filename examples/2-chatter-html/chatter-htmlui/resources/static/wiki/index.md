# 2-chatter-html / chatter-htmlui

## Description

The Chatter HTML UI module provides a super simple and plain user interface to the Chatter Server. A user may set their alias and then send chat messages to all other users.

## Module exemplifies
* Static content
* HTML templates (dynamic content)
* JAXB binding
* XSLT used to transform XML into HTML
* Publishing service methods to be invoked from the web using `@PulsarMethod` annotations
* Consuming services
* Reloading of template part using JS method `reloadPulsarTag()`

## Usage instructions
Click on the follwing link to get to the interface page:
[`/pulsar/pulsarbyexample.chatter.htmlui/chatter.html`](/pulsar/pulsarbyexample.chatter.htmlui/chatter.html)

1. Enter a username and press `Login`
2. Enter a message and press `Say`
3. Open several browser sessions (different browsers or different machines) and see messages sync

## Excersises
1. Extend the message list with a column show how many seconds ago the messge was sent. Hint: Check out the XSL template.

## References


## Links
* [Wiki top](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index)