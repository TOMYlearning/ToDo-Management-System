package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;

@Controller
public class MainController {
	@GetMapping("/mainForm")
	String loginForm(Model model) {
		// ArrayListの宣言・初期化 //
		ArrayList<ArrayList<LocalDate>> month = new ArrayList<>();
		ArrayList<LocalDate> week = new ArrayList<>();
		//日付指定 ////////////////////////////////////////////////////
		int plusMonth = 0,minusMonth = 0,val = 0,countday = 0,countweek = 0,lastday = 0;
		DayOfWeek dayOfWeek;
		LocalDate today = LocalDate.now()
				.plusMonths(plusMonth)
				.minusMonths(minusMonth)
				.withDayOfMonth(1);
		
		LocalDate day  = LocalDate.now()
				.withDayOfMonth(1);
		
		// 初週 ////////////////////////////////////////////////////////////
		dayOfWeek = DayOfWeek.from(today);
		val = dayOfWeek.getValue();
		// ArrayListの宣言・初期化 //
		week = new ArrayList<>();
		// 1st week set //
		for (countday = 0; countday <= 6 ; countday++) {
			day  = LocalDate.now()
					.plusMonths(plusMonth)
					.minusMonths(minusMonth)
					.withDayOfMonth(1)
					.minusDays(val-countday);
			week.add(day);
			// 土曜日取得 //
			if (countday == 6) {
				lastday = day.getDayOfMonth() + 1;
				// 要素の追加 //
				month.add(week);
			}
		}
		// 2週目以降 //////////////////////////////////////////////////////////
		for (countweek = 1; countweek <= 6; countweek++) {
			// ArrayListの宣言・初期化 //
			week = new ArrayList<>();
			if (today.getMonth() == day.getMonth()) {
				for (countday = 0; countday <= 6; countday++) {
					day = LocalDate.now()
							.plusMonths(plusMonth)
							.minusMonths(minusMonth)
							.withDayOfMonth(lastday)
							.plusDays(countday);
					week.add(day);
					//土曜日取得 //
					if (countday == 6) {
						lastday += countday + 1;
						// 要素の追加 //
						month.add(week);
					}
				}
			}
		}
		model.addAttribute("matrix",month);
		
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();

		model.addAttribute("tasks",tasks);

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