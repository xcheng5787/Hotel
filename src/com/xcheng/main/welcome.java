package com.xcheng.main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class welcome {
	public static int STATUS_HAS_GUEST = 0; // �пͻ��޷���ס
	public static int STATUS_EMPTY = 1; // �޿ͻ�������ס

	public static void main(String[] args) {
		Room[] rooms = inIt();
		// System.out.println(rooms[10].toString());
		System.out.println("��ӭ����XX�Ƶ�");
		boolean flag = true;
		while (flag) {
			Scanner sc = new Scanner(System.in);
			tip();
			String str = sc.next();
			if ("search".equals(str)) {
				// �û���ѯ���з���״̬
				System.out.println("�û���ѯ���з���״̬");
				// ��ʾ���з���
				showRooms(rooms);
			} else if ("in".equals(str)) {
				// �û���Ҫ��ס
				System.out.println("����������Ҫ��ס�ķ������Ȼ��س�");
				String sInNUm = sc.next();
				if (!isInteger(sInNUm)) {
					System.out.println("������������");
					continue;
				}
				int checkNum = Integer.valueOf(sInNUm);
				int status = checkStatus(checkNum, rooms);
				// ���״̬Ϊ���пͻ�
				if (status == STATUS_HAS_GUEST) {
					alreadyHasGuest(rooms);
				} else if (status == STATUS_EMPTY) {
					// ����� ������ס
					// TODO
					System.out.println("������ס,��������������������ס");
					String guestName = sc.next();
					checkIn(guestName, checkNum, rooms);
				}

			} else if ("out".equals(str)) {
				// �û���Ҫ�˷�
				// ���û���Ҫ�˷���ʱ������out����ʾ�û�������ס�ķ���ţ��������ķ����״̬Ϊ���ա���ʾ�÷���û��ס�ˣ���ʾ�������룬����Ե�ʱ�����ʾ�û��˷��ɹ������ı䷿���״̬
				System.out.println("��������Ҫ�˷��ķ����");
				int checkOutNum = sc.nextInt();
				int status = checkStatus(checkOutNum, rooms);
				boolean flag_checkOut = true;
				while (flag_checkOut) {
					if (status == STATUS_EMPTY) {
						System.out.println("���Ŵ���,����������");
						checkOutNum = sc.nextInt();
						status = checkStatus(checkOutNum, rooms);
					} else if (status == STATUS_HAS_GUEST) {
						String status_OK = "true";
						if (setRoom(checkOutNum, rooms, status_OK)) {
							System.out.println("�˷��ɹ�");
							flag_checkOut = false;
						}
					}
				}

			} else if ("quit".equals(str)) {
				// �û���Ҫ�˳�
				System.out.println("�û���Ҫ�˳�");
				flag = false;
			}

		}
	}

	private static boolean setRoom(int checkOutNum, Room[] rooms, String status_OK) {
		// TODO �˷� �ı䷿���״̬

		int num = getIndex(checkOutNum, rooms);
		rooms[num].setStatus(status_OK);
		if (checkStatus(checkOutNum, rooms) == STATUS_EMPTY) {
			return true;
		}
		return false;
	}

	private static void checkIn(String guestName, int checkNum, Room[] rooms) {
		// TODO ���� :String �û����� int �����
		// ͬʱ�ѷ����״̬��Ϊ�û�����������ӡ���û���ס�÷���ɹ�
		int num = getIndex(checkNum, rooms);
		rooms[num].setStatus(guestName);
		System.out.println(guestName + "��ϲ��ס" + checkNum + "�ŷ���ɹ�");
	}

	/*
	 * �Ƽ����ٶ���� �ж��Ƿ�Ϊ����
	 * 
	 * @param str ������ַ���
	 * 
	 * @return ����������true,���򷵻�false
	 */

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	private static void alreadyHasGuest(Room[] rooms) {
		// TODO ����������ס����еķ���
		System.out.println("�÷�������ס��,���������뷿�����Ȼ��س�");

	}

	private static int checkStatus(int num, Room[] rms) {
		// TODO ��ѯ����״̬
		for (int i = 0; i < rms.length; i++) {
			if (num == rms[i].getId()) {
				if ("true" == rms[i].getStatus()) {
					return STATUS_EMPTY;
				} else {
					return STATUS_HAS_GUEST;
				}
			}
		}
		return 0;
	}

	private static int getIndex(int num, Room[] rms) {
		for (int i = 0; i < rms.length; i++) {
			if (num == rms[i].getId()) {
				return i;
			}
		}
		return 0;

	}

	private static void showRooms(Room[] rooms) {
		// TODO ��ʾ���з��� ���������̨
		int count = 0;
		System.out.println("==================");
		for (int i = 0; i < rooms.length; i++) {
			if (i == rooms.length - 1) {
				System.out.print(rooms[i].toString() + "\t");
				System.out.println();
			} else if (count < 10) {
				System.out.print(rooms[i].toString() + "\t");
				count++;
			} else {
				System.out.println();
				count = 0;
				System.out.print(rooms[i].toString() + "\t");
				count++;
			}
		}
		System.out.println("==================");

	}

	private static void tip() {
		// TODO ��ʾ�û�����
		System.out.println("��������Ҫ��������:");
		System.out.println("1:����search��ѯ���з���״̬");
		System.out.println("2:����in��ס����");
		System.out.println("3:����out�˷�");
		System.out.println("4:����quit�˳�");
	}

	private static Room[] inIt() {
		// TODO ��ʼ���Ƶ�״̬
		// ��������
		// 9�� ÿ��10������ = 90������
		// ÿ������ID 101 102...
		Room[] rooms = new Room[90];
		// �����鸳ֵ
		rooms = addRooms(rooms);
		return rooms;
	}

	private static Room[] addRooms(Room[] rooms) {
		// TODO Auto-generated method stub
		int num = 0;
		for (int i = 0; i < 9; i++) {
			int tmp = (i + 1) * 100;
			for (int j = 0; j < 10; j++) {
				tmp = tmp + 1;
				rooms[num] = new Room();
				rooms[num].setId(tmp);
				rooms[num].setStatus("true");
				num++;
			}
		}
		return rooms;
	}
}
