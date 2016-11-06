/*
 * The MIT License
 *
 * Copyright (c) 2016 Grigory Chernyshev.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.grundic.agentPriority.config;

import com.github.grundic.agentPriority.prioritisation.AgentPriority;
import jetbrains.buildServer.serverSide.SProject;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * User: g.chernyshev
 * Date: 06/11/16
 * Time: 15:53
 */
public class ConfigurationHelper {

    @NotNull
    private final static String CONFIG_NAME = "agent-priorities.xml";

    private static File getProjectConfig(@NotNull SProject project) {
        return new File(project.getConfigDirectory(), CONFIG_NAME);
    }

    public static void save(@NotNull SProject project, @NotNull AgentPriorityConfiguration configuration) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AgentPriorityConfiguration.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.marshal(configuration, getProjectConfig(project));
    }


    @NotNull
    public static List<AgentPriority> load(@NotNull SProject project) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AgentPriorityConfiguration.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        AgentPriorityConfiguration configuration = (AgentPriorityConfiguration) jaxbUnmarshaller.unmarshal(getProjectConfig(project));
        return configuration.getPriorities();
    }
}
