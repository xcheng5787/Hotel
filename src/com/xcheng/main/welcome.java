package com.xcheng.main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class welcome {
	public static int STATUS_HAS_GUEST = 0; // 有客户无法入住
	public static int STATUS_EMPTY = 1; // 无客户可以入住

	public static void main(String[] args) {
		Room[] rooms = inIt();
		// System.out.println(rooms[10].toString());
		System.out.println("欢迎来到XX酒店");
		boolean flag = true;
		while (flag) {
			Scanner sc = new Scanner(System.in);
			tip();
			String str = sc.next();
			if ("search".equals(str)) {
				// 用户查询所有房间状态
				System.out.println("用户查询所有房间状态");
				// 显示所有房间
				showRooms(rooms);
			} else if ("in".equals(str)) {
				// 用户想要入住
				System.out.println("请输入您想要入住的房间号码然后回车");
				String sInNUm = sc.next();
				if (!isInteger(sInNUm)) {
					System.out.println("输入内容有误");
					continue;
				}
				int checkNum = Integer.valueOf(sInNUm);
				int status = checkStatus(checkNum, rooms);
				// 如果状态为已有客户
				if (status == STATUS_HAS_GUEST) {
					alreadyHasGuest(rooms);
				} else if (status == STATUS_EMPTY) {
					// 房间空 可以入住
					// TODO
					System.out.println("可以入住,请输入您的姓名办理入住");
					String guestName = sc.next();
					checkIn(guestName, checkNum, rooms);
				}

			} else if ("out".equals(str)) {
				// 用户想要退房
				// 当用户需要退房的时候，输入out，提示用户输入入住的房间号，如果输入的房间号状态为“空”表示该房间没有住人，提示重新输入，输入对的时候就提示用户退房成功，并改变房间的状态
				System.out.println("请输入想要退房的房间号");
				int checkOutNum = sc.nextInt();
				int status = checkStatus(checkOutNum, rooms);
				boolean flag_checkOut = true;
				while (flag_checkOut) {
					if (status == STATUS_EMPTY) {
						System.out.println("房号错误,请重新输入");
						checkOutNum = sc.nextInt();
						status = checkStatus(checkOutNum, rooms);
					} else if (status == STATUS_HAS_GUEST) {
						String status_OK = "true";
						if (setRoom(checkOutNum, rooms, status_OK)) {
							System.out.println("退房成功");
							flag_checkOut = false;
						}
					}
				}

			} else if ("quit".equals(str)) {
				// 用户想要退出
				System.out.println("用户想要退出");
				flag = false;
			}

		}
	}

	private static boolean setRoom(int checkOutNum, Room[] rooms, String status_OK) {
		// TODO 退房 改变房间的状态

		int num = getIndex(checkOutNum, rooms);
		rooms[num].setStatus(status_OK);
		if (checkStatus(checkOutNum, rooms) == STATUS_EMPTY) {
			return true;
		}
		return false;
	}

	private static void checkIn(String guestName, int checkNum, Room[] rooms) {
		// TODO 参数 :String 用户姓名 int 房间号
		// 同时把房间的状态改为用户的姓名，打印出用户入住该房间成功
		int num = getIndex(checkNum, rooms);
		rooms[num].setStatus(guestName);
		System.out.println(guestName + "恭喜入住" + checkNum + "号房间成功");
	}

	/*
	 * 推荐，速度最快 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	private static void alreadyHasGuest(Room[] rooms) {
		// TODO 房间已有人住后进行的方法
		System.out.println("该房间有人住了,请重新输入房间号码然后回车");

	}

	private static int checkStatus(int num, Room[] rms) {
		// TODO 查询房间状态
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
		// TODO 显示所有房间 输出到控制台
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
		// TODO 提示用户输入
		System.out.println("输入你想要做的事情:");
		System.out.println("1:输入search查询所有房间状态");
		System.out.println("2:输入in入住房间");
		System.out.println("3:输入out退房");
		System.out.println("4:输入quit退出");
	}

	private static Room[] inIt() {
		// TODO 初始化酒店状态
		// 创建房间
		// 9层 每层10个房间 = 90个房间
		// 每个房间ID 101 102...
		Room[] rooms = new Room[90];
		// 给数组赋值
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
