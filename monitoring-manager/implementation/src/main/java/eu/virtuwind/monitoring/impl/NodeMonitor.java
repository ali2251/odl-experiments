/*
 * Copyright © 2015 Intracom Telecom and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package eu.virtuwind.monitoring.impl;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import org.opendaylight.controller.liblldp.HexEncode;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.ReadFailedException;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNodeConnectorBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;


import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorUpdatedBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.ethernet.rev140528.ethernet.packet.received.packet.chain.packet.EthernetPacketBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.ethernet.rev140528.ethernet.packet.received.packet.chain.packet.EthernetPacket;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.*;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TopologyId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.TopologyKey;


import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.opendaylight.yang.gen.v1.urn.opendaylight.packet.arp.rev140528.KnownHardwareType.Ethernet;

public class NodeMonitor {

	private static final Logger LOG = LoggerFactory
			.getLogger(NodeMonitor.class);

	private PacketProcessingService packetProcessingService;

	public NodeMonitor(PacketProcessingService packetProcessingService) {
		this.packetProcessingService = packetProcessingService;
	}


	public static List<org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Node> getAllLinks(DataBroker db) {
		List<org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Node> nodeList = new ArrayList<>();

		try {
			//Topology Id
			TopologyId topoId = new TopologyId("flow:1");
			//get the InstanceIdentifier
			InstanceIdentifier<Topology> nodesIid = InstanceIdentifier.builder(NetworkTopology.class).child(Topology.class, new TopologyKey(topoId)).toInstance();
			ReadOnlyTransaction nodesTransaction = db.newReadOnlyTransaction();

			//Read from operational database
			CheckedFuture<Optional<Topology>, ReadFailedException> nodesFuture = nodesTransaction
					.read(LogicalDatastoreType.OPERATIONAL, nodesIid);
			Optional<Topology> nodesOptional = nodesFuture.checkedGet();

			if (nodesOptional != null && nodesOptional.isPresent()) {
				nodeList = nodesOptional.get().getNode();
			}
			return nodeList;
		} catch (Exception e) {
			LOG.info("Node Fetching Failed");
			return nodeList;
		}

	}

	public  void transmitPacket() {


        org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress mac = new org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress("aa:aa:aa:aa:aa:aa");

        try {


        byte[] macInBytes =  HexEncode.bytesFromHexString(mac.getValue());

        Ethernet eth = new Ethernet().setSourceMACAddress(macInBytes);



			NodeId nodeId = new NodeId("openflow:1");
            NodeConnectorId nodeConnectorId = new NodeConnectorId("openflow:1:1");

           NodeConnectorRef nodeConnectorRef = new NodeConnectorRef(InstanceIdentifier.builder(Nodes.class)
                    .child(Node.class, new NodeKey(nodeId))
                    .child(NodeConnector.class, new NodeConnectorKey(nodeConnectorId))
                    .build());

            InstanceIdentifier<Node> nodeIID = nodeConnectorRef.getValue()
                    .firstIdentifierOf(Node.class);

             NodeRef nodeRef = new NodeRef(nodeIID);



        TransmitPacketInput packet = new TransmitPacketInputBuilder()
                .setEgress(new NodeConnectorRef(nodeConnectorRef))
                .setNode(nodeRef)
                .setPayload(eth.serialize()).build();

            packetProcessingService.transmitPacket(packet);

        } catch (Exception e) {
            e.printStackTrace();

        }
	}

}