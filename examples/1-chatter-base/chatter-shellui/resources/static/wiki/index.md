%%% prettify install=true
...
%%%


# Module - 1-chatter-base / chatter-shellui

## Description
The Chatter Shell UI module implements a command line shell which allows a user to sign in and send messages to the Chat Server.

## Module exemplifies
* Using internal libraries (Cliche)

## Architectual considerations


## Usage instructions
Install the chatter-shellui module using the `Install` screen in `AdminConsole`.
## Commands
The shell implements who commands `login '<name>'` and `say '<message>'`

    chattershell> login Roy
    13:58:45 [pulsarbyexample.chatter.shellui:run] DEBUG s.d.p.c.server.ChatterServerImpl - [login] Roy is logging in
    chattershell> say Hello
    chattershell> 
	    Roy > 'Hell


## References
* [Cliche Command-Line Shell](https://code.google.com/p/cliche/) available under [MIT License](http://opensource.org/licenses/mit-license.php)

## Links
* [Wiki top](/pulsar/rs/se.dse.pulsar.devtools.wiki.api.Wiki/index)