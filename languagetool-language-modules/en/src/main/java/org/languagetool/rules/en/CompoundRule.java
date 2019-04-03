/* LanguageTool, a natural language style checker 
 * Copyright (C) 2006 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules.en;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.languagetool.Language;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.AbstractCompoundRule;
import org.languagetool.rules.CompoundRuleData;
import org.languagetool.rules.Example;
import org.languagetool.rules.patterns.PatternToken;
import org.languagetool.rules.patterns.PatternTokenBuilder;
import org.languagetool.tagging.disambiguation.rules.DisambiguationPatternRule;

/**
 * Checks that compounds (if in the list) are not written as separate words.
 */
public class CompoundRule extends AbstractCompoundRule {

  // static to make sure this gets loaded only once:
  private static final CompoundRuleData compoundData = new CompoundRuleData("/en/compounds.txt");
  private static final Language AMERICAN_ENGLISH = new AmericanEnglish();
  private static List<DisambiguationPatternRule> antiDisambiguationPatterns = null;
  private static final List<List<PatternToken>> ANTI_PATTERNS = Arrays.asList(
      Arrays.asList(
          new PatternTokenBuilder().tokenRegex("['´]").build(),
          new PatternTokenBuilder().token("re").build()
        )
      );

  public CompoundRule(ResourceBundle messages) throws IOException {    
    super(messages,
            "This word is normally spelled with hyphen.", 
            "This word is normally spelled as one.", 
            "This expression is normally spelled as one or with hyphen.",
            "Hyphenation problem");
    addExamplePair(Example.wrong("I now have a <marker>part time</marker> job."),
                   Example.fixed("I now have a <marker>part-time</marker> job."));
    //by ahsen new compound rules
    addExamplePair(Example.wrong("It is written on the <marker>note-book</marker>."),
      Example.fixed("It is written on the <marker>notebook</marker>."));
    addExamplePair(Example.wrong("This <marker>waist-coat</marker> is green."),
      Example.fixed("This <marker>waistcoat</marker> is green."));
    addExamplePair(Example.wrong("I gotta go to <marker>book-store</marker>."),
      Example.fixed("I gotta go to <marker>bookstore</marker>."));
    addExamplePair(Example.wrong("This <marker>fire-man</marker> saved the baby."),
      Example.fixed("This <marker>fireman</marker> saved the baby."));
    addExamplePair(Example.wrong("The <marker>moon-light</marker> is bright."),
      Example.fixed("The <marker>moonlight</marker> is bright."));
    addExamplePair(Example.wrong("He is a <marker>zoo-keeper</marker>."),
      Example.fixed("He is a <marker>zookeeper</marker>."));
    //end of by ahsen
    //by Lumpus99
    addExamplePair(Example.wrong("The bear dropped down on all fours and growled at her <marker>off-spring</marker>."),
      Example.fixed("The bear dropped down on all fours and growled at her <marker>offspring</marker>."));
    addExamplePair(Example.wrong("His gaze went to the <marker>in-coming</marker> storm."),
      Example.fixed("His gaze went to the <marker>incoming</marker> storm."));
    addExamplePair(Example.wrong("If the <marker>net-work</marker> doesn't work, there's a radio in your car."),
      Example.fixed("If the <marker>network</marker> doesn't work, there's a radio in your car."));
    addExamplePair(Example.wrong("She folded the <marker>news-paper</marker>"),
      Example.fixed("She folded the <marker>newspaper</marker>"));
    addExamplePair(Example.wrong("She started typing on her <marker>key-board</marker>"),
      Example.fixed("She started typing on her <marker>keyboard</marker>"));
    addExamplePair(Example.wrong("He moved the <marker>joy-stick</marker> that was controlling the helicopter."),
      Example.fixed("He moved the <marker>joystick</marker> that was controlling the helicopter."));
    //end of by Lumpus99
  }

  @Override
  public String getId() {
    return "EN_COMPOUNDS";
  }

  @Override
  public String getDescription() {
    return "Hyphenated words, e.g., 'case-sensitive' instead of 'case sensitive'";
  }

  @Override
  protected CompoundRuleData getCompoundRuleData() {
    return compoundData;
  }

  @Override
  public List<DisambiguationPatternRule> getAntiPatterns() {
    if (antiDisambiguationPatterns == null) {
      antiDisambiguationPatterns = makeAntiPatterns(ANTI_PATTERNS, AMERICAN_ENGLISH);
    }
    return antiDisambiguationPatterns;
  }
}
