package monsters;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestIn {
	public static void main(String[] args) {
		PrintWriter out = null, err = null;
		String []patNames = {"SpecStart", "SpecType", "Init", "HP", "AuraStart", "capLine", "AbilityStart"};
		Pattern [] pats = new Pattern[patNames.length];
		pats[0] = Pattern.compile("^((?:[A-Z][,\\w\\-]*)(?:\\s[,\\w\\-]+)*(?:\\s?\\([,\\w\\-\\s]*\\))?)\\s(?:Level\\s(\\d+)\\s)(\\s?\\w+(?:\\s\\w+)?)\\s?+(?:\\(((Leader))\\))?$");
		//pats[1] = Pattern.compile("^.*\\(([^\\)]+)\\)\\s.*XP\\s((?:\\d*,?)+)$");
		pats[1] = Pattern.compile("^([A-Z]\\w*)\\s(\\w*)\\s(\\w*(?:\\s\\w+)?)\\s?(?:\\(([^\\)]+)\\))?(?:,\\s(\\w+))?\\sXP\\s((\\d*,?)+)$");
		pats[2] = Pattern.compile("^Initiative .*$");
		pats[3] = Pattern.compile("^HP .*$");
		pats[4] = Pattern.compile("^([A-Z].*)\\saura\\s([^\\;]+);\\s?((.*))$");
		pats[5] = Pattern.compile("^[A-Zrm].*$");
		pats[6] = Pattern.compile("^([mrMRCA]?)\\s?([A-Z][^A-Z](?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?(?:\\s?[A-Za-z’'\\-]+)?)(?:\\s?(?:\\(([^(\\)|;)]+);?\\s?([^\\)]*)\\)?)?)(?:\\s[\\?\\u2726]\\s?((\\s?[A-Za-z]+[,;]?)+)?)?$");
		Pattern speed = Pattern.compile("^Speed.*$");
		Pattern align = Pattern.compile("^Align.*$");
		Pattern quickLvl = Pattern.compile("^[A-Za-z;’'\\s\\,\\?\\u2726\\u2680-\\u2685\\-\\(\\)]+$");
		Pattern p = Pattern.compile("^.+aura.*;.*$");
		Pattern abil1 = Pattern.compile("^([mrMRCA]?)\\s?([A-Z][^A-Z](?:\\s?[A-Za-z]+)*?).*$");
		Pattern abil3 = Pattern.compile("^.*(?:\\s?(?:\\(([^(\\)|;)]+);?\\s?([^\\)]*)\\)?)?)(?:\\s[\\?\\u2726]\\s((,?\\s?\\[A-Za-z]+)+))?$");
		Pattern abist = Pattern.compile("^.*((\\([^\\)]*)|.*,)$");
		Pattern abilt = Pattern.compile("^.*,$");
		Pattern Trait = Pattern.compile("^(.+)(([A-Z][^A-Z]*))$");
		Pattern Trt = Pattern.compile("^((.+))\\s(([A-Z][^A-Z]*))$");
	try {
		//FileInputStream fread = new FileInputStream("C:\\Documents and Settings\\HP_Administrator.HP-ADMIN\\My Documents\\PaulH\\creations\\DnDMM.txt");
		FileInputStream fread = new FileInputStream("E:\\Downloads\\DnDMM.txt");
		
		InputStreamReader isr = new InputStreamReader(fread, "UTF-16");
		BufferedReader buff = new BufferedReader(isr);
		out = new PrintWriter(System.out, true);
		err = new PrintWriter(System.err, true);
		String strLine, trtLine = null;
		Matcher m, m2, m3, mq, mb;
		int i, j;
		byte [] byt = new byte[2];
		boolean inArea = false;
		String traitStart, traitDesc;
		
		strLine = buff.readLine();
		while ( strLine != null) {
			//out.println(strLine);
			m = p.matcher(strLine);
			m2 = pats[6].matcher(strLine);
			/*
			if (m.matches() && !m2.matches()) {
			
				out.println(strLine);
			}
			/*/if (m2.matches()) {
				out.println(strLine);
				for (i = 2; i < m2.groupCount(); ++i) {
					out.print("[" + m2.group(i) + "]");
				}
				out.println();
			}//*/
			strLine = buff.readLine();
		}
		/*
		strLine = buff.readLine();
		while ( strLine != null) {
			//out.println(strLine);
			m = p.matcher(strLine);
			m2 = pats[6].matcher(strLine);
			mb = abist.matcher(strLine);
			m3 = abilt.matcher(strLine);
			//out.println(":1:");
			trtLine = "";
			//*
			if (m.matches() && m2.matches()) {
				if (mb.matches() || m3.matches()) {
					trtLine = strLine;
					strLine = buff.readLine(); // part of ability, or not first after ability
					//out.println(strLine);
					m = p.matcher(trtLine + " " + strLine);
					m2 = pats[6].matcher(trtLine + strLine);
					if (m.matches() && m2.matches()) {
						for (i = 1; i < m2.groupCount(); ++i) {
							out.print("[" + m2.group(i) + "]");
						}
						out.println();
						strLine = buff.readLine(); // first line after ability
						strLine = buff.readLine();
					} else {
						//out.println(":Not actually Ability");
					}
					
				} else {
					for (i = 1; i < m2.groupCount(); ++i) {
						out.print("[" + m2.group(i) + "]");
					}
					out.println();
					strLine = buff.readLine(); // first line after ability
					strLine = buff.readLine();
				}
				
			} else {
				//out.println(":No");
				strLine = buff.readLine();
			}
		}
			/*//*
			while (m.matches() && m2.matches()) {
				if (trtLine.equals("")) {
					trtLine = strLine;
				} else {
					trtLine = trtLine + " " + strLine;
				}
				strLine = buff.readLine();
				if (strLine == null) {
					break;
				}
				out.println("++" + strLine);
				m = p.matcher(trtLine + " " + strLine);
				m2 = pats[6].matcher(trtLine + " " + strLine);
			}
			out.println(":2:");
			if (!trtLine.equals("")) {
				out.println(trtLine);
				m = p.matcher(trtLine);
				m2 = pats[6].matcher(trtLine);
				if (m.matches() && m2.matches()) {
					for (i = 1; i < m2.groupCount(); ++i) {
						out.print("[" + m2.group(i) + "]");
					}
					out.println();
				}
				
			} else {
				strLine = buff.readLine();
			}
			out.println(":3:");
		}/*//*
		while ( (strLine = buff.readLine()) != null) {
			if (inArea) {
				out.println("1: " + strLine);
				m = align.matcher(strLine);
				if (m.matches()) {
					out.println("alignment");
					out.println("> " + trtLine);
					inArea = false;
					continue;
				}
				out.println(">>>Not Alignment");
				m = pats[5].matcher(strLine);
				mq = quickLvl.matcher(strLine);
				m2 = abil1.matcher(strLine);
				m3 = abil3.matcher(strLine);
				if (m.matches() && mq.matches()) {
					out.println(">>>correct stuff");
					m = pats[6].matcher(strLine);
					if (m.matches()) {
						out.println(">>>Found new ability");
						out.println(">prev> " + trtLine);
						if (m.group(3) != "" && m.group(5) == "") {
							trtLine = buff.readLine();
							out.println("2: " + trtLine);
							out.println(">>>only half of line picked up");
							m2 = pats[6].matcher(strLine + " " + trtLine);
							if (m2.matches()) {
								out.println(">>>appended to trait starter");
								out.println(">>" + strLine + " " + trtLine);
							} else {
								out.println(">>>doesn't cause match, autoappend line");
								trtLine = strLine + " " + trtLine;
								continue;
							}
							
						}
						trtLine = buff.readLine();
						out.println("3: " + trtLine);
						out.println(">>>line after abil, auto appended");
						trtLine = strLine + " " + trtLine;
						continue;
					}
					out.println(">>>right stuff, wrong order, appending");
					trtLine += " " + strLine;
					continue;
				}
				out.println(">>>not right stuff, appending");
				trtLine += " " + strLine;
			} else {
				m = speed.matcher(strLine);
				if (m.matches()) {
					out.println(">>>found speed, nulling");
					inArea = true;
					trtLine = "";
				}
			}
		}//*/
		out.println(trtLine);
		
		
		out.println("Yay");
		fread.close();
	} catch (Exception e) {
		err.println(e);
		System.err.println(e);
		out.println("Boo");
	}
	}
}
