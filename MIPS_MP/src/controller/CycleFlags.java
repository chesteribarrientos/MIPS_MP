/**
 * 
 */
package controller;

/**
 * @author Chester
 *
 */
public class CycleFlags {

	private boolean ifFlag;
	private boolean idFlag;
	private boolean exFlag;
	private boolean memFlag;
	private boolean wbFlag;
	
	public CycleFlags(){
		ifFlag = false;
		idFlag = false;
		exFlag = false;
		memFlag = false;
		wbFlag = false;
	}

	public boolean IFisActive() {
		return ifFlag;
	}

	public boolean IDisActive() {
		return idFlag;
	}

	public boolean EXisActive() {
		return exFlag;
	}

	public boolean MEMisActive() {
		return memFlag;
	}

	public boolean WBisActive() {
		return wbFlag;
	}

	public void IFactive(boolean ifFlag) {
		this.ifFlag = ifFlag;
	}

	public void IDactive(boolean idFlag) {
		this.idFlag = idFlag;
	}

	public void EXactive(boolean exFlag) {
		this.exFlag = exFlag;
	}

	public void MEMactive(boolean memFlag) {
		this.memFlag = memFlag;
	}

	public void WBactive(boolean wbFlag) {
		this.wbFlag = wbFlag;
	}
	
}
