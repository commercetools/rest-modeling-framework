package io.vrap.rmf.nodes

import io.vrap.rmf.nodes.antlr.NodeToken
import org.eclipse.emf.ecore.util.EcoreUtil
import spock.lang.Shared
import spock.lang.Specification

class NodeTokenCollectorTest extends Specification {
    @Shared
    NodeTokenCollector nodeTokenCollector = new NodeTokenCollector()

    def "reparsing works correctly"() {
        when:
        Node node = new NodeModelBuilder().parseYaml('''\
        test: me
        ''')
        List<NodeToken> tokens = nodeTokenCollector.doSwitch(node)
        then:
        Node parsedNode = new NodeModelBuilder().parse(tokens)

        EcoreUtil.equals(parsedNode, node)
    }
}
