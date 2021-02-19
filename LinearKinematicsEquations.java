public class LinearKinematicsEquations {
    public static class CannotSolveException extends Exception {}

    public static double findDisplacement(Double a, Double vf, Double vi, Double t) throws CannotSolveException {
        double x;

        //If no time is elapsed, there is no displacement.
        if(t != null && t == 0) {
            x = 0.0;
            return x;
        }

        //If we have vi, vf, and t we also have a.
        if(vi != null && vf != null && t != null) {
            a = (vf - vi)/t;
        }

        //If a = 0 displacement can be found with either vi or vf.
        if((vf != null || vi != null) && a == 0 && t != null) {
            if(vi != null) x = t * vf;
            else x = vi * t;
            return x;
        }

        //If we have all but t, find x.
        if(vf != null && vi != null && a != null) {
            x = (vf * vf - vi * vi)/(2 * a);
            return x;
        }

        //If we have all but vf, find x.
        if(vi != null && a != null && t != null) {
            x = vi * t + (.5 * a * (t * t));
            return x;
        }

        //If we have all but vi, find x.
        if(vi == null && vf != null && a != null && t != null) {
            x = vf * t - (.5 * a * (t * t));
            return x;
        }

        throw new CannotSolveException();
    }

    public static double findAcceleration(Double x, Double vf, Double vi, Double t) throws CannotSolveException {
        double a;

        //If there is no displacement or delta v, a is 0.
        if((vf != null && vi != null && vf - vi == 0) || (x != null && x == 0) || (t != null && t == 0)) {
            a = 0.0;
            return a;
        }

        //If we have all but x, find a.
        if(vi != null && vf != null && t != null) {
            a = (vf - vi)/t;
            return a;
        }

        //If we have all but t, find a.
        if(t == null && vi != null && vf != null && x != null) {
            a = .5 * (vf * vf - vi * vi)/x;
            return a;
        }

        //If we have all but vi, find a.
        if(vi == null && vf != null && x != null && t != null) {
            a = -2 * ((x -vf * t)/(t * t));
            return a;
        }

        //If we have all but vf, find a.
        if(vf == null && vi != null && x != null && t != null) {
            a = 2 * ((x - vi * t)/(t * t));
            return a;
        }

        throw new CannotSolveException();
    }

    public static double findFinalVelocity(Double x, Double a, Double vi, Double t) throws CannotSolveException {
        double vf;

        //If a is 0, vf equals vi.
        if(vi != null && ((a != null && a == 0) || (t != null && t == 0))) {
            vf = vi;
            return vf;
        }

        //If we have all but a, find vf.
        if(x != null && t != null && vi != null) {
            vf = 2 * (x / t) - vi;
            return vf;
        }

        //If we have all but t, find vf.
        if(x != null && a != null && vi != null) {
            vf = Math.sqrt((vi * vi) + 2 * a * x);
            return vf;
        }

        //If we have all but vi, find vf.
        if(x != null && t != null && a != null) {
            if(t == 0) throw new CannotSolveException();
            vf = (x + .5 * a * (t * t))/t;
            return vf;
        }

        //If we have all but x, find vf.
        if(vi != null && a != null && t != null) {
            vf = vi + a * t;
            return vf;
        }

        throw new CannotSolveException();
    }

    public static double findInitialVelocity(Double x, Double a, Double vf, Double t) throws CannotSolveException {
        double vi;

        //If a is 0, vi equals vf.
        if(vf != null && a != null && (a == 0)) {
            vi = vf;
            return vi;
        }

        //If we have all but a, find vi.
        if(x != null && t != null && vf != null) {
            if(t == 0) return vf;
            vi = 2 * (x/t) - vf;
            return vi;
        }

        //If we have all but t, find vi.
        if(x != null && a != null && vf != null) {
            if(((vf * vf) - 2 * a * x ) < 0) throw new CannotSolveException();
            vi = Math.sqrt((vf * vf) - 2 * a * x);
            return vi;
        }

        //If we have all but vf, find vi.
        if(x != null && t != null && a != null) {
            if(t == 0) throw new CannotSolveException();
            vi = (x - .5 * a * (t * t))/t;
            return vi;
        }

        //If we have all but x, find vf.
        if(vf != null && a != null && t != null) {
            vi = -(a * t - vf);
            return vi;
        }

        throw new CannotSolveException();
    }

    public static double findTime(Double x, Double a, Double vf, Double vi) throws CannotSolveException {
        double t;

        //If we have all but x, find t.
        if(a != null && vi != null && vf != null) {
            if(a == 0) throw new CannotSolveException();
            t = (vf - vi)/a;
            return t;
        }

        //If we have all but a, find t.
        if(x != null && vi != null && vf != null) {
            if(x == 0) return 0.0;
            t = 1/(((vi + vf)/2)/x);
            return t;
        }

        //If we have all but vf, find t.
        if(a != null && vi != null && x != null) {
            if(a == 0) return (vi/x);
            t = (findFinalVelocity(x, a, vi, null) - vi)/a;
            return t;
        }

        //If we have all but vi, find t.
        if(a != null && x != null && vf != null) {
            if(a == 0) return (vf/x);
            t = (vf - findInitialVelocity(x, a, vf, null))/a;
            return t;
        }

        throw new CannotSolveException();
    }

    public static void main(String[] args) {
        //Test
        try {
            System.out.print(findAcceleration(398.0, 112.0, 0.0, null));
        }
        catch(Exception e) {
            System.out.print("Not enough data given");
        }
    }

    /*
    ISSUES:
     - Some fringe cases may not be properly addressed.
     - Dividing by 0, as well as rooting a negative, is not always handled.
     */
}
