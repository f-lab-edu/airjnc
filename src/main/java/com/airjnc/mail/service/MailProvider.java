package com.airjnc.mail.service;

import com.airjnc.mail.dto.SendUsingTemplateDto;

// 특정 기술에 종속적이지 않는 인터페이스
public interface MailProvider {

  // SendUsingTemplateDto -> Template 에서 사용할 데이터들만 담아있기 때문에, 특정 cloud에 종속적이지 않은 데이터이다.
  void send(String email, SendUsingTemplateDto sendUsingTemplateDto);
}
