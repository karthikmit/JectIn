package com.jectin.service;

import org.stringtemplate.v4.ST;

public class MailMessagesHelper {

    public static String getSignupHelloMessage(String name, String activcode) {
        String template = "\nHello <name>,\n\n";

        template += "Thanks a lot for signing up with Ject.in\n\n";

        template += "Your activation code is, <activcode>\n\n";

        template += "Now you could sign in to Ject.in and start using secured services like Safe store. We will keep updating the site with more and more innovative products.";

        template += "\n\nAny comments or ideas are welcome.";

        template += "\n\nThanks,\nJect-in Team.";

        ST strtemplate = new ST(template);
        strtemplate.add("name", name);
        strtemplate.add("activcode", activcode);

        return strtemplate.render();
    }
}