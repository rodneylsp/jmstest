package com.jmstest.servlet;

import com.jmstest.util.Configuration;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/producer")
public class ProducerMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mensagem = "";
        String text = req.getParameter("text") != null? req.getParameter("text") : "Hello World";

        try {

            Context ic = new InitialContext();

            Configuration cfg = new Configuration();

            ConnectionFactory cf = (ConnectionFactory) ic.lookup(cfg.getConnectionFactory());
            Queue fila = (Queue) ic.lookup(cfg.getQueue());

            Connection connection = cf.createConnection();

            Session session = connection.createSession(
                    false, //define se mensagem é parte de uma transação ou não
                    Session.AUTO_ACKNOWLEDGE // segundo parametro somente é usado se o primeiro é false
            );
            MessageProducer publisher = session.createProducer(fila);

            connection.start();

            TextMessage message = session.createTextMessage(text);
            publisher.send(message);

            mensagem = "Mensagem enviada: " + text;
        } catch (NamingException e) {
            mensagem = "Erro ao tentar enviar a mensagem: " + text + "\nError: " + e.getMessage();
        } catch (JMSException e) {
            mensagem = "Erro ao tentar enviar a mensagem: " + text + "\nError: " + e.getMessage();
        }

        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<body>");
        html.append("<p>" + mensagem + "</p>");
        html.append("</body>");
        html.append("</html>");

        resp.getWriter().println(html.toString());
    }
}
