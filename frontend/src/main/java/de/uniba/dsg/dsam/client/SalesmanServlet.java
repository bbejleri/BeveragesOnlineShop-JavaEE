package de.uniba.dsg.dsam.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;
import de.uniba.dsg.dsam.persistence.SalesManagement;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/path")
public class SalesmanServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
       
  @EJB
  private BeverageManagement beverageBean;
  private SalesManagement salesmanBean;
  private IncentiveManagement incentiveBean;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		String button = request.getParameter("submitbutton");
		
		if(button.equals("View Beverages")){
			
		List<Beverage>all_beverages = new ArrayList<Beverage>();
		all_beverages = beverageBean.viewBeverages();
		request.setAttribute("beverages", all_beverages);
	    getServletConfig().getServletContext().getRequestDispatcher("/backendUI.jsp").forward(request, response);
		
		}
		
		else if(button.equals("Create Beverages")){
			String n = request.getParameter("bevname");
			String i = request.getParameter("incname");
			String m = request.getParameter("manu");
			Double p = Double.parseDouble(request.getParameter("price"));
			Integer q = Integer.parseInt(request.getParameter("quantity"));
			beverageBean.create(n, i, m, p, q);	
		}
		
        else if(button.equals("Assign Incentive")){
			String actualname = request.getParameter("actualincname");
			String newname = request.getParameter("newincname");
			beverageBean.assign(actualname, newname);
		}
		
        else if(button.equals("Add Promo Gift")){
        	String inc_name_promo = request.getParameter("newincentivenamepromo");
        	incentiveBean.createInitiativePromo(inc_name_promo);
        }
		
        else if(button.equals("Add Trial Package")){
        	String inc_name_trial = request.getParameter("newincentivenametrial");
        	incentiveBean.createInitiativePromo(inc_name_trial);
        }
		
        else if(button.equals("Edit Incentive")){
        	String inc_to_edit = request.getParameter("oldincname");
        	String new_inc_name = request.getParameter("neweditname");
        	incentiveBean.editInitiative(inc_to_edit, new_inc_name);
        }
		
        else if(button.equals("Delete Incentive")){
        	String inc_to_delete = request.getParameter("inctodelete");
        	incentiveBean.deleteInitiative(inc_to_delete);
        }
		
		else if(button.equals("View Report 1")){
		     
			Double revenue_1 = salesmanBean.ordersRevenue();
		    request.setAttribute("simpleRevenue", revenue_1);
		    getServletConfig().getServletContext().getRequestDispatcher("/backendUI.jsp").forward(request, response);
		     
		}
		
		else if(button.equals("View Report 2")){
			
			String revenue_2 = salesmanBean.brokenDownRevenue();
			request.setAttribute("detailedRevenue", revenue_2);
		}
		
		
	}

}
