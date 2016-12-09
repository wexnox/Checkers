package menu;
import java.awt.event.*;
import javax.swing.*;

public class MenuGraphics {

	private static JMenuItem buildMenuItem(Object item, Object target) {
		JMenuItem r = null;
		if (item instanceof String)
			r = new JMenuItem((String) item);
		else if (item instanceof JMenuItem)
			r = (JMenuItem) item;
		else
			return null;

		if (target instanceof ActionListener)
			r.addActionListener((ActionListener) target);
		return r;
	}

	public static JMenu buildVertikalmenu(Object parent, Object[] items, Object target) {
		JMenu m = null;
		if (parent instanceof JMenu)
			m = (JMenu) parent;
		else if (parent instanceof String)
			m = new JMenu((String) parent);
		else
			return null;

		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				m.addSeparator();
			else
				m.add(buildMenuItem(items[i], target));
		}

		return m;
	}

	public static JPopupMenu buildPopupMenu(Object[] items, Object target) {
		JPopupMenu m = new JPopupMenu();

		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				m.addSeparator();
			else
				m.add(buildMenuItem(items[i], target));
		}

		return m;
	}
}
