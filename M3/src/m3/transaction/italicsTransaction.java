/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class italicsTransaction implements jTPS_Transaction {
    Node n;
    m3Workspace ws;
    
    public italicsTransaction(Node n,m3Workspace ws)
    {
        this.n=n;
        this.ws=ws;        
    }
    
    @Override
    public void doTransaction()
    { 
        ws.processItalics(n);
    }
    
    @Override
    public void undoTransaction()
    {
        ws.processItalics(n);
    }
}
