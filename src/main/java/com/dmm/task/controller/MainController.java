package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/mainForm")
	String loginForm(Model model) {
		// ArrayListの宣言・初期化 //
				ArrayList<ArrayList<LocalDate>> Month = new ArrayList<>();
				
				//日付指定 ////////////////////////////////////////////////////
				int plusMonth = 0,minusMonth = 0,val = 0,countday = 0,countweek = 0,lastday = 0;
				DayOfWeek dayOfWeek;
				LocalDate week = LocalDate.now()
						.plusMonths(plusMonth)
						.minusMonths(minusMonth)
						.withDayOfMonth(1);
				System.out.println(week);
				
				LocalDate weekday  = LocalDate.now()
						.withDayOfMonth(1);
				
				// 初週 ////////////////////////////////////////////////////////////
				dayOfWeek = DayOfWeek.from(week);
				val = dayOfWeek.getValue();
				// ArrayListの宣言・初期化 //
				ArrayList<LocalDate> weeks = new ArrayList<>();
				// 1st week set //
				for (countday = 0; countday <= 6 ; countday++) {
					weekday  = LocalDate.now()
							.plusMonths(plusMonth)
							.minusMonths(minusMonth)
							.withDayOfMonth(1)
							.minusDays(val-countday);
					weeks.add(weekday);
					// 土曜日取得 //
					if (countday == 6) {
						lastday = weekday.getDayOfMonth() + 1;
						// 要素の追加 //
						Month.add(weeks);
					}
				}
				// 2週目以降 //////////////////////////////////////////////////////////
				for (countweek = 1; countweek <= 6; countweek++) {
					// ArrayListの宣言・初期化 //
					weeks = new ArrayList<>();
					if (week.getMonth() == weekday.getMonth()) {
						for (countday = 0; countday <= 6; countday++) {
							weekday = LocalDate.now()
									.plusMonths(plusMonth)
									.minusMonths(minusMonth)
									.withDayOfMonth(lastday)
									.plusDays(countday);
							weeks.add(weekday);
							//土曜日取得 //
							if (countday == 6) {
								lastday += countday + 1;
								// 要素の追加 //
								Month.add(weeks);
							}
						}
					}
				}
				model.addAttribute("month",weekday.getMonth());

		return "main";
	}
	
}

/*

1. 2次元表になるので、ListのListを用意する

2. 1週間分のLocalDateを格納するListを用意する

3. その月の1日のLocalDateを取得する

4. 曜日を表すDayOfWeekを取得し、上で取得したLocalDateに曜日の値（DayOfWeek#getValue)をマイナスして前月分のLocalDateを求める

5. 1日ずつ増やしてLocalDateを求めていき、2．で作成したListへ格納していき、1週間分詰めたら1．のリストへ格納する

6. 2週目以降は単純に1日ずつ日を増やしながらLocalDateを求めてListへ格納していき、土曜日になったら1．のリストへ格納して新しいListを生成する（月末を求めるにはLocalDate#lengthOfMonth()を使う）

7. 最終週の翌月分をDayOfWeekの値を使って計算し、6．で生成したリストへ格納し、最後に1．で生成したリストへ格納する

8. 管理者は全員分のタスクを見えるようにする

*/