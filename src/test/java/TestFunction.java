import java.util.Arrays;

public class TestFunction {
    public static void main(String[] args) {
        String name = "思达鸭24H智能自习中心（南坪协信店）";
        String str = "desc:\"这家店不错哦，一起去吧！思达鸭24H智能自习中心（南坪协信店），南岸区江南大道24号金，信大厦16-9（协信星光旁芒果KTV楼上），17723034169。\"";
        String[] datas = str.substring(str.indexOf(name) + name.length() + 1, str.lastIndexOf("。\"")).split("，");
        System.out.println(Arrays.toString(Arrays.copyOf(datas, datas.length-1)));
    }
}
