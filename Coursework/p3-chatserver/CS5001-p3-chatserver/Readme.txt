The following features are implemented as part of the IRC Server :

1. The user will be able to send commands with whitespaces before the commanda as this will be discarded by the server.
2. The user command will check for the values 0 and * specifically at the respective position, even if they are treated to be meaningless(they are used for permissions in full IRC, but you can ignore
them for this practical).
3. The commands passed from the client can be case insensitive i.e both "nick" and "NICK" will be treated as a valid command.
4. Invalid command - Error will be thrown is the user passed a invalid command other than the ones specified in the specification.