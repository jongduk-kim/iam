package sixt.run;

public class PMT {
	/**
	 * PMT
	 * ���������� �����ϰ� ������ ������ ����Ǵ� ���⿡ ���� ��ȸ ���Ծ��� ����մϴ�.
	 *
	 * @param   rate   �Ⱓ�� �����Դϴ�.
	 * @param   nper   ���� ������ �� Ƚ���Դϴ�.
	 * @param   pv     ���� ��ġ�� ������ ������ �Ϸ��� ���Ա��� ���� ������
	 *                 �ִ� ��ġ�� ����(����)�Դϴ�.
	 * @param   fv     �̷� ��ġ �Ǵ� ���� ���� ���� ���� �ܰ��Դϴ�.
	 *                 fv�� �����ϸ� 0���� ���ֵ˴ϴ�.
	 *                 ��, ������� �̷� ��ġ�� 0�Դϴ�.
	 * @param   type   0 �Ǵ� 1�� �����ϴ� ������ ǥ���մϴ�.
	 *                 0 : �⸻
	 *                 1 : ����
	 * @return  double  pmt
	 * @author  ssari
	 * @since   2004-10-11
	 */
	public static double pmt(double rate, double nper, double pv, double fv, int type) {
		double amt = 0;

		if (rate == 0) {
			amt = (- pv - fv) / nper;
		} else {
			amt = (- fv * rate - pv * rate * Math.pow(1 + rate, nper)) / ((1 + rate * type) * (Math.pow(1 + rate, nper) - 1));
		}

		return	amt;
	}
	
	public static void main(String[] args) {
		
		//double rate = 6.7 / 12;
		double rate = 0.067 / 12;
		double nper = 48;
		double pv = -24000000;
		double fv = 0;
		int type = 0;
		double amt = PMT.pmt(rate, nper, pv, fv, type);
		System.out.println(amt); // -\103,700

//		rate = 0.08 / 12;
//		nper = 10;
//		pv = 1000000;
//		fv = 0;
//		type = 1;
//		amt = PMT.pmt(rate, nper, pv, fv, type);
//		System.out.println(amt); // -$103,000
//
//		rate = 0.12 / 12;
//		nper = 5;
//		pv = -500000;
//		fv = 0;
//		type = 0;
//		amt = PMT.pmt(rate, nper, pv, fv, type);
//		System.out.println(amt); // \103,000
//
//		rate = 0.06 / 12;
//		nper = 18 *12;
//		pv = 0;
//		fv = 5000000;
//		type = 0;
//		amt = PMT.pmt(rate, nper, pv, fv, type);
//		System.out.println(amt); // -\12,900

	}

}
