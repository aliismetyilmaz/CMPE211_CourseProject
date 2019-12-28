package chess.GUI;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ChessGUIcreator implements ActionListener {

	private final HashMap<String, ButtonHandler> buttonMap = new HashMap();
	private final HashMap<String, ButtonGroup> buttonGroups = new HashMap();
	private final HashMap<String, JLabel> labelMap = new HashMap();
	private static final HashMap<String, Integer> messageTypeMap = new HashMap();
	protected final JFrame frame;

	public void display(boolean visible) {
		if (visible) {
			this.frame.pack();
		}
		this.frame.setVisible(visible);
	}

	protected ChessGUIcreator(String title, boolean exitOnClose) {
		this.frame = new JFrame(title);
		this.frame.setUndecorated(true);
		this.frame.getRootPane().setWindowDecorationStyle(1);
		this.frame.setLayout(new GridBagLayout());
		if (exitOnClose) {
			this.frame.setDefaultCloseOperation(3);
		}
	}

	protected void addMenuButton(String label, Object receiver, String funcName) {
		String[] names = label.split("->");
		JMenu menu = this.getMenu(names, names.length - 2);
		JMenuItem item = new JMenuItem(names[names.length - 1]);
		item.setActionCommand(label);
		item.addActionListener(this);
		menu.add(item);
		this.buttonMap.put(label, new ButtonHandler(funcName, receiver, label, item));
	}

	protected void addMenuButton(String label, String funcName) {
		this.addMenuButton(label, this, funcName);
	}

	protected void add(Widget widget, LayoutSpec layout) {
		this.frame.add((Component) widget.me, layout.params);
	}

	protected void addLabel(String text, String id, LayoutSpec layout) {
		JLabel label = new JLabel(text);
		this.labelMap.put(id, label);
		this.frame.add((Component) label, layout.params);
	}

	protected void setLabel(String id, String text) {
		JLabel label = this.labelMap.get(id);
		label.setText(text);
	}

	protected void addLabel(String text, LayoutSpec layout) {
		this.frame.add((Component) new JLabel(text), layout.params);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof AbstractButton) {
			String key = e.getActionCommand();
			ButtonHandler h = this.buttonMap.get(key);
			if (h == null) {
				return;
			}
			try {
				h.func.invoke(h.receiver, h.id);
			} catch (IllegalAccessException excp) {
				System.err.printf("not allowed to call %s.%n", h.func.getName());
			} catch (InvocationTargetException excp) {
				System.err.printf("call to %s caused exception: %s.%n", h.func.getName(), excp.getCause());
			}
		}
	}

	private JMenu getMenu(String[] label, int last) {
		if (this.frame.getJMenuBar() == null) {
			this.frame.setJMenuBar(new JMenuBar());
		}
		JMenuBar bar = this.frame.getJMenuBar();
		JMenu menu = null;
		for (int i = 0; i < bar.getMenuCount() && !(menu = bar.getMenu(i)).getText().equals(label[0]); ++i) {
			menu = null;
		}
		if (menu == null) {
			menu = new JMenu(label[0]);
			bar.add(menu);
		}
		for (int k = 1; k <= last; ++k) {
			JMenu menu0 = menu;
			menu = null;
			for (int i2 = 0; i2 < menu0.getItemCount(); ++i2) {
				JMenuItem item = menu0.getItem(i2);
				if (item == null)
					continue;
				if (item.getText().equals(label[k])) {
					if (item instanceof JMenu) {
						menu = (JMenu) item;
						break;
					}
					throw new IllegalStateException("inconsistent menu label");
				}
				menu = null;
			}
			if (menu != null)
				continue;
			menu = new JMenu(label[k]);
			menu0.add(menu);
		}
		return menu;
	}

	private static class ButtonHandler extends Handler {
		ButtonHandler(String funcName, Object receiver, String id, AbstractButton src) {
			super(funcName, receiver, id, src, String.class);
		}
	}

	/*
	 * This class specifies class file version 49.0 but uses Java 6 signatures.
	 * Assumed Java 6.
	 */
	private static class Handler {
		Method func;
		Object receiver;
		String id;

		/* varargs */ Handler(String funcName, Object receiver, String id, AbstractButton src, Class<?>... args) {
			this.receiver = receiver;
			this.id = id;
			if (funcName == null) {
				this.func = null;
			} else {
				try {
					this.func = receiver.getClass().getDeclaredMethod(funcName, args);
					this.func.setAccessible(true);
				} catch (NoSuchMethodException e) {
					throw new IllegalArgumentException("Method not found or wrong arguments: " + funcName);
				}
			}
		}
	}

}
