package test.jmocket.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

import mockit.integration.junit4.JMockit;

/**
 * From: http://www.baeldung.com/jmockit-101
 * Source: https://github.com/eugenp/tutorials/tree/master/testing-modules/mocks/jmockit/src
 * @author HSO
 *
 */
@RunWith(JMockit.class)
public class PerformerTest {

    @Injectable
    private Collaborator collaborator;

    @Tested
    private Performer performer;

    @Test
    public void testThePerformMethod(@Mocked Model model) {
    	new Expectations() {{
    		model.getInfo();result = "bar";
    		collaborator.collaborate("bar"); result = true;
    	}};

    	performer.perform(model);

    	new Verifications() {{
    		collaborator.receive(true);
    	}};
    }

}

